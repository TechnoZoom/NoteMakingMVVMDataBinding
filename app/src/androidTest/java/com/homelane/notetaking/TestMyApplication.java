package com.homelane.notetaking;

import com.homelane.notetaking.di.AppComponent;
import com.homelane.notetaking.di.DaggerAppComponent;
import com.homelane.notetaking.di.NotesTestModule;
import com.homelane.notetaking.di.modules.NotesModule;

/**
 * Created by kapilbakshi on 10/08/17.
 */

public class TestMyApplication extends  MyApplication {

    @Override
    public AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .notesModule(new NotesTestModule(this))
                .build();
    }
}
