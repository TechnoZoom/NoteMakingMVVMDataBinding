package com.kapil.ecomm;

import android.app.Application;

import com.kapil.ecomm.di.AppComponent;
import com.kapil.ecomm.di.DaggerAppComponent;
import com.kapil.ecomm.di.modules.NetworkModule;
import com.kapil.ecomm.di.modules.NotesModule;
import com.kapil.ecomm.di.modules.OrdersModule;

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
