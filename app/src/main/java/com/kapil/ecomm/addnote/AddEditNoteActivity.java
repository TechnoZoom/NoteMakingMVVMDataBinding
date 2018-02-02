package com.kapil.ecomm.addnote;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kapil.ecomm.MyApplication;
import com.kapil.ecomm.R;
import com.kapil.ecomm.data.source.NotesRepository;
import com.kapil.ecomm.data.source.local.entities.Note;
import com.kapil.ecomm.databinding.ActivityAddEditNoteBinding;
import com.kapil.ecomm.util.SnackbarUtils;

import javax.inject.Inject;

public class AddEditNoteActivity extends AppCompatActivity {

    private ActivityAddEditNoteBinding activityAddEditNoteBinding;
    private AddEditNoteViewModel viewModel;
    public static final String ARGUMENT_EDIT_NOTE_ID = "edit_note_id";
    private Observable.OnPropertyChangedCallback snackbarCallback;
    private Observable.OnPropertyChangedCallback noteSavedCallback;

    @Inject
    NotesRepository notesRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getComponent().inject(this);
        activityAddEditNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_note);
        AddEditNoteViewModel.Factory addEditViewModelFactory = new AddEditNoteViewModel.Factory(getApplication(), notesRepository);
        viewModel = ViewModelProviders.of(this, addEditViewModelFactory).get(AddEditNoteViewModel.class);
        activityAddEditNoteBinding.contAdEdNote.setViewmodel(viewModel);
        setLiveDataObserverCallbacks();
        setListeners();
    }

    private void setLiveDataObserverCallbacks() {
        setupSnackBarTextLiveDataObserverListener();
        setNotesSavedLiveDataObserver();
    }

    private void setListeners() {
        activityAddEditNoteBinding.contAdEdNote.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.saveNote();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadNote(getIntent().getStringExtra(ARGUMENT_EDIT_NOTE_ID));
        setNoteLoadedLiveDataListener();
    }

    private void setNoteLoadedLiveDataListener() {
        if (viewModel.getNoteLiveData() != null) {
            viewModel.getNoteLiveData().observe(this, new Observer<Note>() {
                @Override
                public void onChanged(Note note) {
                    /* Simply a call back so that Transformations.switchMap() in AddEditViewModel
                    can observe changes */
                }
            });
        }
    }

    private void setupSnackBarTextLiveDataObserverListener() {
        if (viewModel.getSnackbarTextLiveData() != null) {
            viewModel.getSnackbarTextLiveData().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String snackBarText) {
                    SnackbarUtils.showSnackbar(activityAddEditNoteBinding.coord, snackBarText);

                }
            });
        }
    }

    private void setNotesSavedLiveDataObserver() {
        if (viewModel.getNoteSavedLiveData() != null) {
            viewModel.getNoteSavedLiveData().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean isNoteSaved) {
                    if (isNoteSaved != null && isNoteSaved) {
                        finish();
                    }
                }
            });
        }
    }

    /*@Override
    public void onDestroy() {

        if (noteSavedCallback != null) {
            viewModel.noteSavedObservable.removeOnPropertyChangedCallback(noteSavedCallback);
        }
        super.onDestroy();
    }*/
}
