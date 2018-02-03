package com.kapil.ecomm.data.source.mock;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.kapil.ecomm.data.source.NotesDataSource;
import com.kapil.ecomm.data.source.local.entities.Note;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class FakeNotesSource implements NotesDataSource {

    private static FakeNotesSource INSTANCE;

    private static final Map<String, Note> NOTES_SERVICE_DATA = new LinkedHashMap<>();

    private static String FAKE_NOTE_TITLE_1 = "FAKE TITLE 1";
    private static String FAKE_NOTE_DESC_1 = "FAKE DESC 1";

    public static String FAKE_NOTE_UPDATED_TITLE = "FAKE NOTE UPDATED TITLE";

    private FakeNotesSource() {}

    public static FakeNotesSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeNotesSource();
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Note>> getNotes() {
        return null;
    }

    @Override
    public LiveData<Note> getNote(@NonNull String noteId) {
        return null;
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
    public void deleteNote(@NonNull Note note) {

    }

    @VisibleForTesting
    public void addNotes(Note... notes) {
        if (notes != null) {
            for (Note task : notes) {
                NOTES_SERVICE_DATA.put(task.getId(), task);
            }
        }
    }

    public static Note createAndFetchFakeNote(String title, String desc) {
        return new Note(title,desc,System.currentTimeMillis());
    }

    public static Note fetchFakeNote() {
        return new Note(FAKE_NOTE_TITLE_1, FAKE_NOTE_DESC_1,System.currentTimeMillis());
    }

    public static List<Note> getFakeNotes(int size) {
        List<Note> noteList = new ArrayList<Note>();
        for(int i = 1; i <= size; i++ ) {
            Note note = new Note("Title " + i,"Desc " + i, System.currentTimeMillis());
            noteList.add(note);
        }
        return noteList;
    }
}