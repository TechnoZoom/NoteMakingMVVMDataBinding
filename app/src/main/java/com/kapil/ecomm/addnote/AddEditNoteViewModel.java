package com.kapil.ecomm.addnote;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kapil.ecomm.R;
import com.kapil.ecomm.data.source.NotesRepository;
import com.kapil.ecomm.data.source.local.entities.Note;
import com.kapil.ecomm.util.DateTimeUtils;

public class AddEditNoteViewModel extends AndroidViewModel {

    public final ObservableField<String> title = new ObservableField<String>();

    public final ObservableField<String> description = new ObservableField<String>();

    public final ObservableField<String> subTitle = new ObservableField<String>();

    public final ObservableField<Boolean> dataLoading = new ObservableField<Boolean>();
    
    public MutableLiveData<String> snackbarTextLiveData = new MutableLiveData<>();

    public MutableLiveData<Boolean> noteSavedLiveData = new MutableLiveData<>();

    private final NotesRepository notesRepository;

    private final Context mContext;

    @Nullable
    private String mNoteId;

    private boolean mIsNewNote;

    private boolean mIsDataLoaded = false;

    public LiveData<Note> getNoteLiveData() {
        return noteLiveData;
    }

    private LiveData<Note> noteLiveData;


    public AddEditNoteViewModel(@NonNull Application application, NotesRepository notesRepository) {
        super(application);
        dataLoading.set(false);
        snackbarTextLiveData.setValue(null);
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

        dataLoading.set(true);
        noteLiveData = Transformations.switchMap(notesRepository.getNote(taskId),note -> {
            if(note != null) {
                description.set(note.getNoteDesc());
                title.set(note.getNoteTitle());
                subTitle.set(note.getSubTitle());
                dataLoading.set(false);
            }
            return notesRepository.getNote(taskId);
        });
        mIsNewNote = false;
    }

    public void saveNote() {
        Note note = new Note(title.get(),subTitle.get(), description.get(), DateTimeUtils.getCurrentEpoch());
        if (note.isEmpty()) {
            snackbarTextLiveData.setValue(mContext.getString(R.string.empty_note_message));
            return;
        }
        if (isNewNote()) {
            createNote(note);
        } else {
            updateNote(note);
        }
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
        notesRepository.updateNote(new Note(note.getNoteTitle(),note.getSubTitle(),note.getNoteDesc(),
                DateTimeUtils.getCurrentEpoch(),mNoteId));
        navigateOnNoteSaved();
    }

    private void navigateOnNoteSaved() {
        noteSavedLiveData.setValue(true);
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
            return (T) new AddEditNoteViewModel(application, notesRepository);
        }
    }

    public MutableLiveData<String> getSnackbarTextLiveData() {
        return snackbarTextLiveData;
    }

    public MutableLiveData<Boolean> getNoteSavedLiveData() {
        return noteSavedLiveData;
    }
}
