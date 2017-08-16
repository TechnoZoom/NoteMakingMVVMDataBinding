package com.homelane.notetaking;

import com.homelane.notetaking.di.AppComponent;
import com.homelane.notetaking.di.DaggerAppComponent;
import com.homelane.notetaking.di.NotesTestModule;
import com.homelane.notetaking.di.OrdersTestModule;
import com.homelane.notetaking.di.modules.NetworkModule;
import com.homelane.notetaking.di.modules.NotesModule;

/**
 * Created by kapilbakshi on 16/08/17.
 */

public class RoboelectricApplication  extends MyApplication {

    @Override
    public AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .notesModule(new NotesTestModule(this))
                .networkModule(new NetworkModule(this))
                .ordersModule(new OrdersTestModule())
                .build();
    }

}

