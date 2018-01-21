
package com.kapil.ecomm.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.kapil.ecomm.data.Note;
import com.kapil.ecomm.data.source.NotesDataSource;
import com.kapil.ecomm.data.source.local.NotesPersistenceContract.TaskEntry;

import java.util.ArrayList;
import java.util.List;


/**
 * Concrete implementation of a data source as a db.
 */
public class NotesLocalDataSource implements NotesDataSource {

    private static NotesLocalDataSource INSTANCE;

    private NotesDbHelper mDbHelper;

    // Prevent direct instantiation.
    private NotesLocalDataSource(@NonNull Context context) {
        mDbHelper = new NotesDbHelper(context);
    }

    public static NotesLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NotesLocalDataSource(context);
        }
        return INSTANCE;
    }

    /**
     * Note: {@link LoadNotesCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getNotes(@NonNull LoadNotesCallback callback) {
        List<Note> notes = new ArrayList<Note>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                NotesPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID,
                NotesPersistenceContract.TaskEntry.COLUMN_NAME_TITLE,
                NotesPersistenceContract.TaskEntry.COLUMN_NAME_DESCRIPTION,
                TaskEntry.COLUMN_DATE_TIME
        };

        Cursor c = db.query(
                TaskEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String itemId = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_ENTRY_ID));
                String title = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_TITLE));
                String description =
                        c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_DESCRIPTION));
               long datetime =  c.getLong(c.getColumnIndexOrThrow(TaskEntry.COLUMN_DATE_TIME));
               Note note = new Note(title, description, datetime, itemId);
               notes.add(note);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (notes.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onNotesLoaded(notes);
        }

    }

    /**
     * Note: {@link GetNotesCallback#onDataNotAvailable()} is fired if the {@link Note} isn't
     * found.
     */
    @Override
    public void getNote(@NonNull String taskId, @NonNull GetNotesCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                TaskEntry.COLUMN_NAME_ENTRY_ID,
                TaskEntry.COLUMN_NAME_TITLE,
                TaskEntry.COLUMN_NAME_DESCRIPTION,
                TaskEntry.COLUMN_DATE_TIME
        };

        String selection = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { taskId };

        Cursor c = db.query(
                TaskEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Note task = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            String itemId = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_ENTRY_ID));
            String title = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_TITLE));
            String description =
                    c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_DESCRIPTION));
            long datetime =  c.getLong(c.getColumnIndexOrThrow(TaskEntry.COLUMN_DATE_TIME));
            task = new Note(title, description, datetime, itemId);
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (task != null) {
            callback.onNoteLoaded(task);
        } else {
            callback.onDataNotAvailable();
        }
    }

    private ContentValues getContentValues(@NonNull Note note) {
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_NAME_TITLE, note.getTitle());
        values.put(TaskEntry.COLUMN_NAME_DESCRIPTION, note.getDescription());
        values.put(TaskEntry.COLUMN_DATE_TIME, note.getDatetime());
        return values;
    }

    @Override
    public void saveNote(@NonNull Note note) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values= getContentValues(note);
        values.put(TaskEntry.COLUMN_NAME_ENTRY_ID, note.getId());
        db.insert(TaskEntry.TABLE_NAME, null,values);
        db.close();
    }

    @Override
    public void updateNote(@NonNull Note note) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.update(TaskEntry.TABLE_NAME, getContentValues(note),
                TaskEntry.COLUMN_NAME_ENTRY_ID+ "=\"" +note.getId() + "\"" , null);
        db.close();
    }

    @Override
    public void deleteAllNotes() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(TaskEntry.TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void deleteNote(@NonNull String taskId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selection = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { taskId };
        db.delete(TaskEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
    }
}
