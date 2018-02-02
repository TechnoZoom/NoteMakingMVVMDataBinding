package com.kapil.ecomm.notes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kapil.ecomm.MyApplication;
import com.kapil.ecomm.R;
import com.kapil.ecomm.addnote.AddEditNoteActivity;
import com.kapil.ecomm.data.source.NotesRepository;
import com.kapil.ecomm.data.source.local.entities.Note;
import com.kapil.ecomm.databinding.ActivityAllNotesBinding;
import com.kapil.ecomm.util.SnackbarUtils;

import java.util.List;

import javax.inject.Inject;

import static com.kapil.ecomm.addnote.AddEditNoteActivity.ARGUMENT_EDIT_NOTE_ID;

public class AllNotesActivity extends AppCompatActivity {

    public static final String NOTES_VIEWMODEL_TAG = "NOTES_VIEWMODEL_TAG";
    private ActivityAllNotesBinding activityAllNotesBinding;
    private AllNotesViewModel allNotesViewModel;
    private Observable.OnPropertyChangedCallback snackbarCallback;
    private Observable.OnPropertyChangedCallback noteClickedCallBack;
    @Inject
    NotesRepository notesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getComponent().inject(this);
        activityAllNotesBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(this.getString(R.string.all_notes));
        setViewModel();
        activityAllNotesBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddEditActivity(null);
            }
        });
        setupSnackBar();
        setNoteClickedCallback();
        //setNotesListCallBack();
    }

    private void setViewModel() {
        AllNotesViewModel.Factory allNotesViewModelFactory = new AllNotesViewModel.Factory(notesRepository);
        allNotesViewModel = ViewModelProviders.of(this, allNotesViewModelFactory).get(AllNotesViewModel.class);
        activityAllNotesBinding.setViewmodel(allNotesViewModel);
    }


    private void startAddEditActivity(String noteId) {
        Intent intent = new Intent(this, AddEditNoteActivity.class);
        intent.putExtra(ARGUMENT_EDIT_NOTE_ID,noteId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        allNotesViewModel.loadNotes();
        setNotesListCallBack();
    }

    private void setupSnackBar() {
        snackbarCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                SnackbarUtils.showSnackbar(activityAllNotesBinding.mainCord, allNotesViewModel.getSnackbarText());
            }
        };
        allNotesViewModel.snackbarText.addOnPropertyChangedCallback(snackbarCallback);
    }

    private void setNoteClickedCallback() {
        noteClickedCallBack = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                startAddEditActivity(allNotesViewModel.getNoteClickedId());
            }
        };
        allNotesViewModel.noteClickedId.addOnPropertyChangedCallback(noteClickedCallBack);
    }

    private void setNotesListCallBack() {

        allNotesViewModel.getNotesLiveData().observe(this,new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                if(notes == null){
                    return;
                }
                setNotesRecyclerView(notes.subList(0,notes.size()));
            }
        });
    }

    private void setNotesRecyclerView(List<Note> noteList) {
        activityAllNotesBinding.contAllNotes.notesRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        activityAllNotesBinding.contAllNotes.notesRecyclerView.setAdapter(new AllNotesAdapter(this,allNotesViewModel,noteList));
    }

}
