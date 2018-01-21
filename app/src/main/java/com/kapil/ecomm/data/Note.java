package com.kapil.ecomm.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;


public final class Note {

    @NonNull
    private final String mId;

    @Nullable
    private final String mTitle;

    @Nullable
    private final String mDescription;

    @Nullable
    private final long datetime;

    /**
     * Constructor to create a new active Note.
     *
     * @param title       title of the note
     * @param description description of the note
     */
    public Note(@Nullable String title, @Nullable String description, @Nullable long datetime) {
        this(title, description, datetime, UUID.randomUUID().toString());
    }

    /**
     * @param title       title of the note
     * @param description description of the note
     * @param id          id of the note
     * @param datetime   timestamp of note creation in human readable format
     */
    public Note(@Nullable String title, @Nullable String description, long datetime,
                @NonNull String id) {
        mId = id;
        mTitle = title;
        mDescription = description;
        this.datetime = datetime;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public long getDatetime() {
        return datetime;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }


    public boolean isEmpty() {
        return (mTitle == null || mTitle.isEmpty())  ||
                (mDescription == null || mDescription.isEmpty());
    }

    @Override
    public String toString() {
        return "Note with title " + mTitle;
    }
}
