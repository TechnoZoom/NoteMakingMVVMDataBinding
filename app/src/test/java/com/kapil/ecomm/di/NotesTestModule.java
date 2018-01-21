package com.kapil.ecomm.di;

import com.kapil.ecomm.MyApplication;
import com.kapil.ecomm.data.source.NotesRepository;
import com.kapil.ecomm.data.source.mock.FakeNotesSource;
import com.kapil.ecomm.di.modules.NotesModule;

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
                FakeNotesSource.getInstance());
    }
}
