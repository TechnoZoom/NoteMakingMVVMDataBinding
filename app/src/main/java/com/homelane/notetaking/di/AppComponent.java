package com.homelane.notetaking.di;

import com.homelane.notetaking.addnote.AddEditNoteActivity;
import com.homelane.notetaking.di.modules.NotesModule;
import com.homelane.notetaking.notes.AllNotesActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by kapilbakshi on 10/08/17.
 */

@Singleton
@Component(modules = {
        NotesModule.class
})
public interface AppComponent {

    void inject(AddEditNoteActivity addEditNoteActivity);

    void inject(AllNotesActivity allNotesActivity);
}
