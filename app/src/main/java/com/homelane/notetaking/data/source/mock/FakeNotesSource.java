package com.homelane.notetaking.data.source.mock;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.homelane.notetaking.data.Note;
import com.homelane.notetaking.data.source.NotesDataSource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class FakeNotesSource implements NotesDataSource {

    private static FakeNotesSource INSTANCE;

    private static final Map<String, Note> NOTES_SERVICE_DATA = new LinkedHashMap<>();

    private FakeNotesSource() {}

    public static FakeNotesSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeNotesSource();
        }
        return INSTANCE;
    }

    @Override
    public void getNotes(@NonNull LoadNotesCallback callback) {
        callback.onNotesLoaded(new ArrayList<>(NOTES_SERVICE_DATA.values()));
    }

    @Override
    public void getNote(@NonNull String noteId, @NonNull GetNotesCallback callback) {
        Note note = NOTES_SERVICE_DATA.get(noteId);
        callback.onNoteLoaded(note);
    }

    @Override
    public void saveNote(@NonNull Note note) {
        NOTES_SERVICE_DATA.put(note.getId(), note);
    }

    @Override
    public void updateNote(@NonNull Note note) {
        NOTES_SERVICE_DATA.put(note.getId(), note);
    }

    @Override
    public void deleteAllNotes() {
        NOTES_SERVICE_DATA.clear();
    }

    @Override
    public void deleteNote(@NonNull String noteId) {
        NOTES_SERVICE_DATA.remove(noteId);
    }

    @VisibleForTesting
    public void addNotes(Note... tasks) {
        if (tasks != null) {
            for (Note task : tasks) {
                NOTES_SERVICE_DATA.put(task.getId(), task);
            }
        }
    }
}
