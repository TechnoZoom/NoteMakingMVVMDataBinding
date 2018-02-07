package com.kapil.ecomm.db.daos;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kapil.ecomm.util.LiveDataTestUtil;
import com.kapil.ecomm.data.source.local.AppDatabase;
import com.kapil.ecomm.data.source.local.daos.NotesDao;
import com.kapil.ecomm.data.source.local.entities.Note;
import com.kapil.ecomm.data.source.mock.FakeNotesSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by kapilbakshi on 03/02/18.
 */

@RunWith(AndroidJUnit4.class)
public class NotesDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase mDatabase;

    private NotesDao notesDao;

    @Before
    public void initDb() throws Exception {
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class)
                .allowMainThreadQueries()
                .build();

        notesDao = mDatabase.notesDao();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void onFetchingNotes_shouldGetEmptyList_IfTable_IsEmpty() throws InterruptedException {
        List<Note> noteList = LiveDataTestUtil.getValue(notesDao.getAllNotes());
        assertTrue(noteList.isEmpty());
    }

    @Test
    public void onInsertingNotes_checkIf_RowCountIsCorrect() throws InterruptedException {
        List<Note> noteList = FakeNotesSource.getFakeNotes(5);
        noteList.forEach(note -> {
            notesDao.insert(note);
        });
        assertEquals(5, LiveDataTestUtil.getValue(notesDao.getAllNotes()).size());
    }


    @Test
    public void onUpdatingANote_checkIf_UpdateHappensCorrectly() throws InterruptedException {
        Note note = FakeNotesSource.fetchFakeNote();
        notesDao.insert(note);
        note.setNoteTitle(FakeNotesSource.FAKE_NOTE_UPDATED_TITLE);
        notesDao.update(note);
        assertEquals(1, LiveDataTestUtil.getValue(notesDao.getAllNotes()).size());
        assertEquals(FakeNotesSource.FAKE_NOTE_UPDATED_TITLE,
                LiveDataTestUtil.getValue(notesDao.getNoteById(note.getNotesId())).getNoteTitle());
    }

    @Test
    public void onNoteDeletion_CheckIf_NoteIsDeletedFromTable() throws InterruptedException {
        List<Note> noteList = FakeNotesSource.getFakeNotes(5);
        noteList.forEach(note -> {
            notesDao.insert(note);
        });
        notesDao.deleteNote(noteList.get(2));
        assertNull(LiveDataTestUtil.getValue(notesDao.getNoteById(noteList.get(2).getNotesId())));
     }

}