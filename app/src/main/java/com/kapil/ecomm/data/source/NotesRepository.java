package com.kapil.ecomm.data.source;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapil.ecomm.data.source.local.entities.Note;

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

    @Override
    public LiveData<List<Note>> getNotes() {
      return mTasksLocalDataSource.getNotes();
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
    public LiveData<Note> getNote(@NonNull final String noteID) {
        return mTasksLocalDataSource.getNote(noteID);
    }

    @Override
    public void deleteAllNotes() {
        mTasksLocalDataSource.deleteAllNotes();
    }

    @Override
    public void deleteNote(@NonNull Note note) {
        mTasksLocalDataSource.deleteNote(note);
    }

}
