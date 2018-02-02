package com.kapil.ecomm.data.source;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapil.ecomm.data.source.local.entities.Note;

import java.util.List;

public class NotesRepository implements NotesDataSource {

    private static NotesRepository INSTANCE = null;
    private final NotesDataSource notesDataSource;

    private NotesRepository(
            @NonNull NotesDataSource tasksLocalDataSource) {
        notesDataSource = tasksLocalDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param notesDataSource the device storage data source
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
        return notesDataSource.getNotes();
    }

    @Override
    public void saveNote(@NonNull Note note) {
        notesDataSource.saveNote(note);
    }

    @Override
    public void updateNote(@NonNull Note note) {
        notesDataSource.updateNote(note);
    }

    @Override
    public LiveData<Note> getNote(@NonNull final String noteID) {
        return notesDataSource.getNote(noteID);
    }

    @Override
    public void deleteAllNotes() {
        notesDataSource.deleteAllNotes();
    }

    @Override
    public void deleteNote(@NonNull Note note) {
        notesDataSource.deleteNote(note);
    }
}
