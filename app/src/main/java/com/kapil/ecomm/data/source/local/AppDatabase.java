package com.kapil.ecomm.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kapil.ecomm.data.source.local.daos.NotesDao;
import com.kapil.ecomm.data.source.local.entities.Note;

/**
 * Created by kapilbakshi on 25/01/18.
 */

@Database(entities = {Note.class}, version = 1)
public abstract  class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "AppDataBase.db";
    private static volatile AppDatabase INSTANCE;

    public abstract NotesDao notesDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
