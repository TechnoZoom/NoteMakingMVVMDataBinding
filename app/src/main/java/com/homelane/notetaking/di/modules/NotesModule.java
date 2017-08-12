package com.homelane.notetaking.di.modules;

import android.app.Application;
import android.content.res.Resources;

import com.homelane.notetaking.MyApplication;
import com.homelane.notetaking.data.source.NotesRepository;
import com.homelane.notetaking.data.source.local.NotesLocalDataSource;

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
