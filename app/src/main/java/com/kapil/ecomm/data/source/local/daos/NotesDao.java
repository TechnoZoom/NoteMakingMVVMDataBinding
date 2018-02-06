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

    //Inserts a particular note in the table
    @Insert
    void insert(Note notesEntity);

    //Deletes a particular note
    @Delete
    void deleteNote(Note note);

    //Deletes all the notes From the table
    @Query("DELETE FROM " + NotesPersistenceContract.TaskEntry.TABLE_NAME)
    public void deleteAllNotes();

    //Updates a particular Note
    @Update
    int update(Note notesEntity);

    //Fetches a particular note from the table
    @Query("SELECT * FROM " + NotesPersistenceContract.TaskEntry.TABLE_NAME +" WHERE " +
            NotesPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID + " = :id")
    LiveData<Note> getNoteById(String id);

    //Fetches the list of notes from the table
    @Query("SELECT * FROM " + NotesPersistenceContract.TaskEntry.TABLE_NAME)
    LiveData<List<Note>> getAllNotes();
}
