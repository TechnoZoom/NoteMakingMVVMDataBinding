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

    @ColumnInfo(name = NotesPersistenceContract.TaskEntry.COLUMN_NAME_DESCRIPTION)
    private String noteDesc;

    @ColumnInfo(name = NotesPersistenceContract.TaskEntry.COLUMN_NAME_SUBTITLE)
    private String subTitle;

    @ColumnInfo(name = NotesPersistenceContract.TaskEntry.COLUMN_DATE_TIME)
    private long noteDateTime;


    @Ignore
    public Note(String noteTitle, String subTitle,  String noteDesc, long noteDateTime) {
        this(noteTitle,subTitle,noteDesc,noteDateTime, UUID.randomUUID().toString());
    }

    public Note(String noteTitle, String subTitle, String noteDesc, long noteDateTime,String notesId) {
        this.notesId = notesId;
        this.noteTitle = noteTitle;
        this.noteDesc = noteDesc;
        this.noteDateTime = noteDateTime;
        this.subTitle = subTitle;
    }


    public boolean isEmpty() {
        return (noteTitle == null || noteTitle.isEmpty())  ||
                (noteDesc == null || noteDesc.isEmpty());
    }

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

    @Override
    public String toString() {
        return "Note with title " + noteTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }
}
