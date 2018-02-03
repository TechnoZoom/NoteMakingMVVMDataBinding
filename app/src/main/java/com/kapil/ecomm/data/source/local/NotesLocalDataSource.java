
package com.kapil.ecomm.data.source.local;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.kapil.ecomm.AppExecutors;
import com.kapil.ecomm.data.source.NotesDataSource;
import com.kapil.ecomm.data.source.local.daos.NotesDao;
import com.kapil.ecomm.data.source.local.entities.Note;

import java.util.List;


/**
 * Concrete implementation of a data source as a db.
 */
public class NotesLocalDataSource implements NotesDataSource {

    private static NotesLocalDataSource INSTANCE;

    private NotesDbHelper mDbHelper;

    private NotesDao notesDao;

    private AppExecutors appExecutors;


    // Prevent direct instantiation.
    private NotesLocalDataSource(@NonNull Context context, NotesDao notesDao) {
        mDbHelper = new NotesDbHelper(context);
        this.notesDao = notesDao;
        this.appExecutors = new AppExecutors();
    }

    public static NotesLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (NotesLocalDataSource.class) {
                AppDatabase appDatabase = AppDatabase.getInstance(context);
                INSTANCE = new NotesLocalDataSource(context, appDatabase.notesDao());
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Note>> getNotes() {
        return notesDao.getAllNotes();
    }

    @Override
    public LiveData<Note> getNote(@NonNull String noteId) {
        return notesDao.getNoteById(noteId);
    }


    @Override
    public void saveNote(@NonNull final Note note) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                notesDao.insert(note);
            }
        });
    }

    @Override
    public void updateNote(@NonNull final Note note) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                notesDao.update(note);
            }
        });
    }

    @Override
    public void deleteAllNotes() {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                notesDao.deleteAllNotes();
            }
        });
    }

    @Override
    public void deleteNote(@NonNull final Note note) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                notesDao.deleteNote(note);
            }
        });
    }
}
