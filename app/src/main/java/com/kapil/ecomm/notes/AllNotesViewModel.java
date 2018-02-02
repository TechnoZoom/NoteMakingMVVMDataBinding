
package com.kapil.ecomm.notes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.kapil.ecomm.data.source.NotesRepository;
import com.kapil.ecomm.data.source.local.entities.Note;

import java.util.List;

public class AllNotesViewModel extends ViewModel {

    // These observable fields will update Views automatically
    private LiveData<List<Note>> notesLiveData;

    public final ObservableBoolean dataLoading = new ObservableBoolean();

    final ObservableField<String> snackbarText = new ObservableField<>();

    final ObservableField<String> noteClickedId = new ObservableField<>();

    private final NotesRepository notesRepository;

    public AllNotesViewModel(NotesRepository repository) {
        notesLiveData = new MutableLiveData<List<Note>>();
        notesRepository = repository;
    }

    public String getNoteClickedId() {
        return noteClickedId.get();
    }


    public void loadNotes() {
        loadNotes(true);
    }

    public void deleteNote(Note note) {
        notesRepository.deleteNote(note);
    }

    public void noteClicked(String noteId) {
        noteClickedId.set(noteId);
    }

    public String getSnackbarText() {
        return snackbarText.get();
    }

    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }

    /**
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadNotes(final boolean showLoadingUI) {
        if (showLoadingUI) {
            dataLoading.set(true);
        }

        notesLiveData = Transformations.switchMap(notesRepository.getNotes(), notes -> {
            if (notes != null) {
                dataLoading.set(false);
            }

            return notesRepository.getNotes();
        });
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final NotesRepository notesRepository;

        public Factory(NotesRepository notesRepository) {
            this.notesRepository = notesRepository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new AllNotesViewModel(notesRepository);
        }
    }
}
