package com.kapil.ecomm.data.source.local.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kapil.ecomm.data.source.local.NotesPersistenceContract;
import com.kapil.ecomm.data.source.local.entities.Note;

import java.util.List;

/**
 * Created by kapilbakshi on 25/01/18.
 */

@Dao
public interface NotesDao {

    @Insert
    void insert(Note notesEntity);

    @Delete
    void deleteAllNote(Note note);

    @Query("DELETE FROM " + NotesPersistenceContract.TaskEntry.TABLE_NAME)
    public void deleteAllNotes();

    @Update
    int update(Note notesEntity);

    @Query("SELECT * FROM " + NotesPersistenceContract.TaskEntry.TABLE_NAME +" WHERE " +
            NotesPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID + " = :id")
    LiveData<Note> getNoteById(String id);

    @Query("SELECT * FROM " + NotesPersistenceContract.TaskEntry.TABLE_NAME)
    LiveData<List<Note>> getAllNotes();
}
