
package com.homelane.notetaking.notes;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import com.homelane.notetaking.R;
import com.homelane.notetaking.data.Note;
import com.homelane.notetaking.data.source.NotesDataSource;
import com.homelane.notetaking.data.source.NotesRepository;
import java.util.List;

public class AllNotesViewModel extends BaseObservable {

    // These observable fields will update Views automatically
    public final ObservableList<Note> items = new ObservableArrayList<>();

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    final ObservableField<String> snackbarText = new ObservableField<>();

    final ObservableField<String> noteClickedId = new ObservableField<>();

    private final NotesRepository notesRepository;

    private Context mContext;

    public AllNotesViewModel(
            NotesRepository repository,
            Context context) {
        mContext = context.getApplicationContext();
        notesRepository = repository;
    }

    public String getNoteClickedId() {
        return noteClickedId.get();
    }


    public void loadNotes() {
        loadNotes(true);
    }

    public void deleteNote(String noteId) {
        notesRepository.deleteNote(noteId);
    }

    public void noteClicked(String noteId) {
        noteClickedId.set(noteId);
    }

    public String getSnackbarText() {
        return snackbarText.get();
    }

    /**
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadNotes(final boolean showLoadingUI) {
        if (showLoadingUI) {
            dataLoading.set(true);
        }
        notesRepository.getNotes(new NotesDataSource.LoadNotesCallback() {

            @Override
            public void onNotesLoaded(List<Note> notes) {
                dataLoading.set(false);
                items.clear();
                items.addAll(notes);
            }

            @Override
            public void onDataNotAvailable() {
                dataLoading.set(false);
                snackbarText.set(mContext.getString(R.string.no_notes_available));
            }
        });
    }
}
