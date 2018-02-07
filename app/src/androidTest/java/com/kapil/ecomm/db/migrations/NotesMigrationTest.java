package com.kapil.ecomm.db.migrations;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kapil.ecomm.data.source.local.AppDatabase;
import com.kapil.ecomm.data.source.local.NotesPersistenceContract;
import com.kapil.ecomm.data.source.local.daos.NotesDao;
import com.kapil.ecomm.data.source.local.entities.Note;
import com.kapil.ecomm.data.source.mock.FakeNotesSource;
import com.kapil.ecomm.util.LiveDataTestUtil;
import com.kapil.ecomm.util.MigrationUtil;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static com.kapil.ecomm.data.source.local.AppDatabase.MIGRATION_1_2;
import static com.kapil.ecomm.data.source.local.AppDatabase.MIGRATION_2_3;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Created by kapilbakshi on 06/02/18.
 */

@RunWith(AndroidJUnit4.class)
public class NotesMigrationTest {

    private static final String TEST_DB_NAME = "TestAppDatabase.db";
    @Rule
    public MigrationTestHelper migrationTestHelper =
            new MigrationTestHelper(
                    InstrumentationRegistry.getInstrumentation(),
                    AppDatabase.class.getCanonicalName(),
                    new FrameworkSQLiteOpenHelperFactory());



    @Test
    public void onMigrationFrom2To3_CheckIf_NotesTableContainsCorrectData() throws IOException, InterruptedException {
        SupportSQLiteDatabase db = migrationTestHelper.createDatabase(TEST_DB_NAME, 2);
        List<Note> noteList = FakeNotesSource.getFakeNotes(5);

        noteList.forEach(note -> {
            insertNote(note,db);
        });
        //Prepare for the next version
        db.close();

        // Re-open the database with version 3 and provide MIGRATION_1_2 and
        // MIGRATION_2_3 as the migration process.
        migrationTestHelper.runMigrationsAndValidate(TEST_DB_NAME, 3, true,
                MIGRATION_1_2, MIGRATION_2_3);

        // MigrationTestHelper automatically verifies the schema changes, but not the data validity
        // Validate that the data was migrated properly.
        AppDatabase appDatabase = (AppDatabase) MigrationUtil.getDatabaseAfterPerformingMigrations(migrationTestHelper,AppDatabase.class,TEST_DB_NAME,
                MIGRATION_1_2, MIGRATION_2_3);

        NotesDao migratedDataNotesDao = appDatabase.notesDao();
        assertEquals(LiveDataTestUtil.getValue(migratedDataNotesDao.getNoteById(noteList.get(3).getNotesId())).getNoteDesc(),
                noteList.get(3).getNoteDesc());
        assertNull(LiveDataTestUtil.getValue(migratedDataNotesDao.getNoteById(noteList.get(3).getNotesId())).getSubTitle());
        appDatabase.close();
    }


    private void insertNote(Note note, SupportSQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(NotesPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID, note.getNotesId());
        values.put(NotesPersistenceContract.TaskEntry.COLUMN_NAME_DESCRIPTION, note.getNoteDesc());
        values.put(NotesPersistenceContract.TaskEntry.COLUMN_NAME_TITLE, note.getNoteTitle());
        values.put(NotesPersistenceContract.TaskEntry.COLUMN_DATE_TIME, note.getNoteDateTime());
        db.insert(NotesPersistenceContract.TaskEntry.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE, values);
    }

}
