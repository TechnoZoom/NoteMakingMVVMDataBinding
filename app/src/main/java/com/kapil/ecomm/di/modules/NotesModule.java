package com.kapil.ecomm.di.modules;

import com.kapil.ecomm.MyApplication;
import com.kapil.ecomm.data.source.NotesRepository;
import com.kapil.ecomm.data.source.local.NotesLocalDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kapilbakshi on 10/08/17.
 */

@Module
public class NotesModule {

    private MyApplication application;

    public NotesModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public NotesRepository providesNotesRepository() {
        return NotesRepository.getInstance(
                NotesLocalDataSource.getInstance(application));
    }

}
