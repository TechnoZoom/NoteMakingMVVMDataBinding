package com.kapil.ecomm.data.source;

import android.support.annotation.NonNull;

import com.kapil.ecomm.data.Note;

import java.util.List;

public class NotesRepository implements NotesDataSource {

    private static NotesRepository INSTANCE = null;
    private final NotesDataSource mTasksLocalDataSource;

    private NotesRepository(
            @NonNull NotesDataSource tasksLocalDataSource) {
        mTasksLocalDataSource = tasksLocalDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param notesDataSource  the device storage data source
     * @return the {@link NotesRepository} instance
     */
    public static NotesRepository getInstance(
            NotesDataSource notesDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new NotesRepository(notesDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(NotesDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets tasks from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadNotesCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getNotes(@NonNull final LoadNotesCallback callback) {
        mTasksLocalDataSource.getNotes(new LoadNotesCallback() {
            @Override
            public void onNotesLoaded(List<Note> tasks) {
                callback.onNotesLoaded(tasks);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveNote(@NonNull Note note) {
        mTasksLocalDataSource.saveNote(note);
    }

    @Override
    public void updateNote(@NonNull Note note) {
        mTasksLocalDataSource.updateNote(note);
    }

    @Override
    public void getNote(@NonNull final String noteId, @NonNull final GetNotesCallback callback) {
        mTasksLocalDataSource.getNote(noteId, new GetNotesCallback() {
            @Override
            public void onNoteLoaded(Note note) {
                callback.onNoteLoaded(note);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteAllNotes() {
        mTasksLocalDataSource.deleteAllNotes();
    }

    @Override
    public void deleteNote(@NonNull String noteId) {
        mTasksLocalDataSource.deleteNote(noteId);
    }

}
