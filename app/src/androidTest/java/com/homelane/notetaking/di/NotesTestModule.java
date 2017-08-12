package com.homelane.notetaking.di;

import com.homelane.notetaking.MyApplication;
import com.homelane.notetaking.data.source.NotesRepository;
import com.homelane.notetaking.data.source.local.NotesLocalDataSource;
import com.homelane.notetaking.data.source.mock.FakeDataSource;
import com.homelane.notetaking.di.modules.NotesModule;

import dagger.Module;

/**
 * Created by kapilbakshi on 10/08/17.
 */

public class NotesTestModule extends NotesModule {

    public NotesTestModule(MyApplication application) {
        super(application);
    }

    @Override
    public NotesRepository providesNotesRepository() {
        return NotesRepository.getInstance(
                FakeDataSource.getInstance());
    }
}
