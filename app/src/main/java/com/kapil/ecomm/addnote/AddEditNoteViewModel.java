package com.kapil.ecomm.addnote;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kapil.ecomm.R;
import com.kapil.ecomm.data.source.NotesRepository;
import com.kapil.ecomm.data.source.local.entities.Note;
import com.kapil.ecomm.util.DateTimeUtils;

public class AddEditNoteViewModel extends AndroidViewModel {

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


    public AddEditNoteViewModel(@NonNull Application application, NotesRepository notesRepository) {
        super(application);
        mContext = application.getApplicationContext();
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
        notesRepository.getNote(taskId);
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
        notesRepository.updateNote(new Note(note.getTitle(), note.getDescription(),
                DateTimeUtils.getCurrentEpoch(),mNoteId));
        navigateOnNoteSaved();
    }

    private void navigateOnNoteSaved() {
        noteSavedObservable.set(true);
    }

    public void setSnackBarText(String textValue) {
        snackbarText.set(textValue);
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private Application application;
        private final NotesRepository notesRepository;

        public Factory(Application application, NotesRepository notesRepository) {
           this.notesRepository = notesRepository;
           this.application = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new AddEditNoteViewModel(application, notesRepository);
        }
    }
}
