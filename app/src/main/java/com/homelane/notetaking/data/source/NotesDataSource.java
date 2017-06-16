package com.homelane.notetaking.data.source;

import android.support.annotation.NonNull;


import com.homelane.notetaking.data.Note;

import java.util.List;


public interface NotesDataSource {

    interface LoadNotesCallback {

        void onNotesLoaded(List<Note> tasks);

        void onDataNotAvailable();
    }

    interface GetNotesCallback {

        void onNoteLoaded(Note task);

        void onDataNotAvailable();
    }

    void getNotes(@NonNull LoadNotesCallback callback);

    void getNote(@NonNull String noteId, @NonNull GetNotesCallback callback);

    void saveNote(@NonNull Note note);

    void updateNote(@NonNull Note note);

    void deleteAllNotes();

    void deleteNote(@NonNull String noteId);
}
