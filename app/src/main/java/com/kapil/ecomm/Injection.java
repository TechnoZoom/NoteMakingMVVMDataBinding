package com.kapil.ecomm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kapil.ecomm.data.source.NotesRepository;
import com.kapil.ecomm.data.source.local.NotesLocalDataSource;

public class Injection {

    public static NotesRepository provideTasksRepository(@NonNull Context context) {
        return com.kapil.ecomm.data.source.NotesRepository.getInstance(
                NotesLocalDataSource.getInstance(context.getApplicationContext()));
    }
}
