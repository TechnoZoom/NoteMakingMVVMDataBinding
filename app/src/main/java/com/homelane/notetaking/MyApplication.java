package com.homelane.notetaking;

import android.app.Application;

import com.homelane.notetaking.di.AppComponent;
import com.homelane.notetaking.di.DaggerAppComponent;
import com.homelane.notetaking.di.modules.NotesModule;

/**
 * Created by kapilbakshi on 10/08/17.
 */

public class MyApplication extends Application {

    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    public AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .notesModule(new NotesModule(this))
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = createComponent();
    }
}
