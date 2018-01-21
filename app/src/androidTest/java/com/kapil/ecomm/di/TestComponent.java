package com.kapil.ecomm.di;

import com.kapil.ecomm.di.modules.NotesModule;
import com.kapil.ecomm.notes.AllNotesActivityTest;
import com.kapil.ecomm.notes.NotesTestsWithSqliteDataSet;

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
