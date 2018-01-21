package com.kapil.ecomm.notes;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.kapil.ecomm.MyApplication;
import com.kapil.ecomm.R;
import com.kapil.ecomm.ViewModelHolder;
import com.kapil.ecomm.addnote.AddEditNoteActivity;
import com.kapil.ecomm.data.Note;
import com.kapil.ecomm.data.source.NotesRepository;
import com.kapil.ecomm.databinding.ActivityAllNotesBinding;
import com.kapil.ecomm.util.ActivityUtils;
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
        setNotesListCallBack();
    }

    private void setViewModel() {
        allNotesViewModel =findOrCreateViewModel();
        activityAllNotesBinding.setViewmodel(allNotesViewModel);
    }

    private AllNotesViewModel findOrCreateViewModel() {
        // In a configuration change we might have a ViewModel present. It's retained using the
        // Fragment Manager.
        @SuppressWarnings("unchecked")
        ViewModelHolder<AllNotesViewModel> retainedViewModel =
                (ViewModelHolder<AllNotesViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(NOTES_VIEWMODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewmodel();
        } else {
            // There is no ViewModel yet, create it.
            AllNotesViewModel viewModel = new AllNotesViewModel(
                    notesRepository,
                    getApplicationContext());
            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    NOTES_VIEWMODEL_TAG);
            return viewModel;
        }
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

        allNotesViewModel.items.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Note>>() {
            @Override
            public void onChanged(ObservableList<Note> notes) {
                setNotesRecyclerView(notes.subList(0,notes.size()));

            }

            @Override
            public void onItemRangeChanged(ObservableList<Note> notes, int i, int i1) {
                setNotesRecyclerView(notes.subList(0,notes.size()));
            }

            @Override
            public void onItemRangeInserted(ObservableList<Note> notes, int i, int i1) {
                setNotesRecyclerView(notes.subList(0,notes.size()));

            }

            @Override
            public void onItemRangeMoved(ObservableList<Note> notes, int i, int i1, int i2) {
                setNotesRecyclerView(notes.subList(0,notes.size()));

            }

            @Override
            public void onItemRangeRemoved(ObservableList<Note> notes, int i, int i1) {
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
