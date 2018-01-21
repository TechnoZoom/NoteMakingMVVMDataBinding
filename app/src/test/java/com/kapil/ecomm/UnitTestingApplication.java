package com.kapil.ecomm;

import com.kapil.ecomm.di.AppComponent;
import com.kapil.ecomm.di.DaggerAppComponent;
import com.kapil.ecomm.di.NotesTestModule;
import com.kapil.ecomm.di.OrdersTestModule;
import com.kapil.ecomm.di.modules.NetworkModule;

/**
 * Created by kapilbakshi on 16/08/17.
 */

public class UnitTestingApplication extends MyApplication {

    @Override
    public AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .notesModule(new NotesTestModule(this))
                .networkModule(new NetworkModule(this))
                .ordersModule(new OrdersTestModule())
                .build();
    }

}

