package com.kapil.ecomm.data.source.local.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.kapil.ecomm.data.source.local.NotesPersistenceContract;

import java.util.UUID;


@Entity(tableName = NotesPersistenceContract.TaskEntry.TABLE_NAME)

public class Note {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = NotesPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID)
    private String notesId;

    @ColumnInfo(name = NotesPersistenceContract.TaskEntry.COLUMN_NAME_TITLE)
    private String noteTitle;

    public String getNotesId() {
        return notesId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteDesc() {
        return noteDesc;
    }

    public long getNoteDateTime() {
        return noteDateTime;
    }

    @ColumnInfo(name = NotesPersistenceContract.TaskEntry.COLUMN_NAME_DESCRIPTION)
    private String noteDesc;

    @ColumnInfo(name = NotesPersistenceContract.TaskEntry.COLUMN_DATE_TIME)
    private long noteDateTime;


    @Ignore
    public Note(String noteTitle, String noteDesc, long noteDateTime) {
        this(noteTitle,noteDesc,noteDateTime, UUID.randomUUID().toString());
    }

    public Note(String noteTitle, String noteDesc, long noteDateTime,String notesId) {
        this.notesId = notesId;
        this.noteTitle = noteTitle;
        this.noteDesc = noteDesc;
        this.noteDateTime = noteDateTime;
    }

    public String getId() {
        return notesId;
    }

    public String getTitle() {
        return noteTitle;
    }

    public String getDescription() {
        return noteDesc;
    }

    public long getDatetime() {
        return noteDateTime;
    }


    public boolean isEmpty() {
        return (noteTitle == null || noteTitle.isEmpty())  ||
                (noteDesc == null || noteDesc.isEmpty());
    }

    @Override
    public String toString() {
        return "Note with title " + noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }
}
