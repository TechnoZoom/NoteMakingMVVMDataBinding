package com.homelane.notetaking.di;

import com.homelane.notetaking.di.modules.NotesModule;
import com.homelane.notetaking.notes.AllNotesActivityTest;
import com.homelane.notetaking.notes.NotesTestsWithSqliteDataSet;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by kapilbakshi on 10/08/17.
 */

@Singleton
@Component(modules = {
        NotesModule.class
})
public interface TestComponent {

    void inject(AllNotesActivityTest allNotesActivityTest);
    void inject(NotesTestsWithSqliteDataSet notesTestsWithSqliteDataSet);
}
