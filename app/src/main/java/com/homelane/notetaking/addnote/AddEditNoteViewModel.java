package com.homelane.notetaking.addnote;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;
import com.homelane.notetaking.R;
import com.homelane.notetaking.data.Note;
import com.homelane.notetaking.data.source.NotesDataSource;
import com.homelane.notetaking.data.source.NotesRepository;
import com.homelane.notetaking.util.DateTimeUtils;

public class AddEditNoteViewModel implements NotesDataSource.GetNotesCallback {

    public ObservableField<String> title = new ObservableField<>();

    public ObservableField<String> description = new ObservableField<>();

    public ObservableBoolean dataLoading = new ObservableBoolean(false);

    public ObservableField<String> snackbarText = new ObservableField<>();

    public ObservableField<Boolean> noteSavedObservable = new ObservableField<>();

    private final NotesRepository notesRepository;

    private final Context mContext;

    @Nullable
    private String mNoteId;

    private boolean mIsNewNote;

    private boolean mIsDataLoaded = false;


    AddEditNoteViewModel(Context context, NotesRepository notesRepository) {
        mContext = context.getApplicationContext(); // Force use of Application Context.
        this.notesRepository = notesRepository;
    }


    public void loadNote(String taskId) {
        if (dataLoading.get()) {
            return;
        }
        mNoteId = taskId;
        if (taskId == null) {
            mIsNewNote = true;
            return;
        }
        if (mIsDataLoaded) {
            return;
        }
        mIsNewNote = false;
        dataLoading.set(true);
        notesRepository.getNote(taskId, this);
    }

    @Override
    public void onNoteLoaded(Note note) {
        title.set(note.getTitle());
        description.set(note.getDescription());
        dataLoading.set(false);
        mIsDataLoaded = true;
    }

    @Override
    public void onDataNotAvailable() {
        dataLoading.set(false);
    }

    public void saveNote() {
        Note note = new Note(title.get(), description.get(), DateTimeUtils.getCurrentEpoch());
        if (note.isEmpty()) {
            snackbarText.set(mContext.getString(R.string.empty_note_message));
            return;
        }
        if (isNewNote()) {
            createNote(note);
        } else {
            updateNote(note);
        }
    }

    @Nullable
    public String getSnackbarText() {
        return snackbarText.get();
    }

    private boolean isNewNote() {
        return mIsNewNote;
    }

    private void createNote(Note note) {
        notesRepository.saveNote(note);
        navigateOnNoteSaved();
    }

    private void updateNote(Note note) {
        if (isNewNote()) {
            throw new RuntimeException("updateNote() was called but note is new.");
        }
        notesRepository.updateNote(new Note(note.getTitle(), note.getDescription(), DateTimeUtils.getCurrentEpoch(),mNoteId));
        navigateOnNoteSaved();
    }

    private void navigateOnNoteSaved() {
        noteSavedObservable.set(true);
    }

    public void setSnackBarText(String textValue) {
        snackbarText.set(textValue);
    }
}
