package com.kapil.ecomm.addnote;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kapil.ecomm.MyApplication;
import com.kapil.ecomm.R;
import com.kapil.ecomm.data.source.NotesRepository;
import com.kapil.ecomm.databinding.ActivityAddEditNoteBinding;
import com.kapil.ecomm.util.SnackbarUtils;

import javax.inject.Inject;

public class AddEditNoteActivity extends AppCompatActivity {

    private ActivityAddEditNoteBinding activityAddEditNoteBinding;
    private AddEditNoteViewModel viewModel;
    public static final String ARGUMENT_EDIT_NOTE_ID = "edit_note_id";
    public static final String ADD_EDIT_VIEWMODEL_TAG = "ADD_EDIT_VIEWMODEL_TAG";
    private Observable.OnPropertyChangedCallback snackbarCallback;
    private Observable.OnPropertyChangedCallback noteSavedCallback;

    @Inject
    NotesRepository notesRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getComponent().inject(this);
        activityAddEditNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_note);
        AddEditNoteViewModel.Factory addEditViewModelFactory = new AddEditNoteViewModel.Factory(getApplication(),notesRepository);
        viewModel = ViewModelProviders.of(this, addEditViewModelFactory).get(AddEditNoteViewModel.class);
        activityAddEditNoteBinding.contAdEdNote.setViewmodel(viewModel);
        setupSnackBar();
        setNoteSavedCallback();
        setListeners();
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
    }

    private void setupSnackBar() {
        snackbarCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if(viewModel.getSnackbarText() != null) {
                    SnackbarUtils.showSnackbar(activityAddEditNoteBinding.coord, viewModel.getSnackbarText());
                    viewModel.setSnackBarText(null);
                }
            }
        };
        viewModel.snackbarText.addOnPropertyChangedCallback(snackbarCallback);
    }

    private void setNoteSavedCallback() {
        noteSavedCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if(viewModel.noteSavedObservable.get()) {
                    finish();
                }
            }
        };
        viewModel.noteSavedObservable.addOnPropertyChangedCallback(noteSavedCallback);
    }

    @Override
    public void onDestroy() {
        if (snackbarCallback != null) {
            viewModel.snackbarText.removeOnPropertyChangedCallback(snackbarCallback);
        }

        if (noteSavedCallback != null) {
            viewModel.noteSavedObservable.removeOnPropertyChangedCallback(noteSavedCallback);
        }
        super.onDestroy();
    }
}
