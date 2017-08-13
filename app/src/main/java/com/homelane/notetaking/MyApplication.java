package com.homelane.notetaking;

import android.app.Application;

import com.homelane.notetaking.di.AppComponent;
import com.homelane.notetaking.di.DaggerAppComponent;
import com.homelane.notetaking.di.modules.NetworkModule;
import com.homelane.notetaking.di.modules.NotesModule;
import com.homelane.notetaking.di.modules.OrdersModule;

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
                .networkModule(new NetworkModule(this))
                .ordersModule(new OrdersModule())
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
            component = createComponent();
    }
}
