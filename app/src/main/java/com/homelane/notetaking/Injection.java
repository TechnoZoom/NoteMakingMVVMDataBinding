package com.homelane.notetaking;

import android.content.Context;
import android.support.annotation.NonNull;

import com.homelane.notetaking.data.source.NotesRepository;
import com.homelane.notetaking.data.source.local.NotesLocalDataSource;

public class Injection {

    public static NotesRepository provideTasksRepository(@NonNull Context context) {
        return com.homelane.notetaking.data.source.NotesRepository.getInstance(
                NotesLocalDataSource.getInstance(context.getApplicationContext()));
    }
}
