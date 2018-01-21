package com.kapil.ecomm;

import com.kapil.ecomm.di.AppComponent;
import com.kapil.ecomm.di.DaggerAppComponent;
import com.kapil.ecomm.di.DaggerTestComponent;
import com.kapil.ecomm.di.NotesTestModule;
import com.kapil.ecomm.di.OrdersTestModule;
import com.kapil.ecomm.di.TestComponent;
import com.kapil.ecomm.di.modules.NetworkModule;
import com.kapil.ecomm.di.modules.NotesModule;

/**
 * Created by kapilbakshi on 10/08/17.
 */

public class TestMyApplication extends  MyApplication {

    @Override
    public AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .notesModule(new NotesTestModule(this))
                .networkModule(new NetworkModule(this))
                .ordersModule(new OrdersTestModule())
                .build();
    }

    public TestComponent getTestAppComponent() {
        return DaggerTestComponent.builder()
                .notesModule(new NotesModule(this))
                .build();
    }
}
