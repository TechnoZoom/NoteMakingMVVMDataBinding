package com.kapil.ecomm.data.source.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.kapil.ecomm.data.source.local.daos.NotesDao;
import com.kapil.ecomm.data.source.local.entities.Note;

/**
 * Created by kapilbakshi on 25/01/18.
 */

@Database(entities = {Note.class}, version = 3)
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
                            .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


   public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // This is because we have migrated from SQLite to Room
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE " + NotesPersistenceContract.TaskEntry.TABLE_NAME
                    + " ADD COLUMN " + NotesPersistenceContract.TaskEntry.COLUMN_NAME_SUBTITLE + " TEXT");
        }
    };
}
