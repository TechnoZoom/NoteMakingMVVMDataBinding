package com.homelane.notetaking;

import com.homelane.notetaking.di.AppComponent;
import com.homelane.notetaking.di.DaggerAppComponent;
import com.homelane.notetaking.di.DaggerTestComponent;
import com.homelane.notetaking.di.NotesTestModule;
import com.homelane.notetaking.di.TestComponent;
import com.homelane.notetaking.di.modules.NetworkModule;
import com.homelane.notetaking.di.modules.NotesModule;
import com.homelane.notetaking.di.modules.OrdersModule;

/**
 * Created by kapilbakshi on 10/08/17.
 */

public class TestMyApplication extends  MyApplication {

    @Override
    public AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .notesModule(new NotesTestModule(this))
                .networkModule(new NetworkModule(this))
                .ordersModule(new OrdersModule())
                .build();
    }

    public TestComponent getTestAppComponent() {
        return DaggerTestComponent.builder()
                .notesModule(new NotesModule(this))
                .build();
    }
}
