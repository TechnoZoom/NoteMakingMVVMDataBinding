package com.kapil.ecomm.data.source;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapil.ecomm.data.source.local.entities.Note;

import java.util.List;


public interface NotesDataSource {

    /*interface LoadNotesCallback {

        void onNotesLoaded(LiveData<List<Note>> tasks);

        void onDataNotAvailable();
    }

    interface GetNotesCallback {

        void onNoteLoaded(LiveData<Note> noteLiveData);

        void onDataNotAvailable();
    }*/

    LiveData<List<Note>> getNotes();

    LiveData<Note> getNote(@NonNull String noteId);

    void saveNote(@NonNull Note note);

    void updateNote(@NonNull Note note);

    void deleteAllNotes();

    void deleteNote(@NonNull Note note);
}
