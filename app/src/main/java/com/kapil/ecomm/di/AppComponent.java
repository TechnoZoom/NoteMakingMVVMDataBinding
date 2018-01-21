package com.kapil.ecomm.di;

import com.kapil.ecomm.addnote.AddEditNoteActivity;
import com.kapil.ecomm.data.source.remote.OrdersRemoteDataSource;
import com.kapil.ecomm.di.modules.NetworkModule;
import com.kapil.ecomm.di.modules.NotesModule;
import com.kapil.ecomm.di.modules.OrdersModule;
import com.kapil.ecomm.notes.AllNotesActivity;
import com.kapil.ecomm.orderlifecycle.AllOrdersActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by kapilbakshi on 10/08/17.
 */

@Singleton
@Component(modules = {
        NotesModule.class, NetworkModule.class, OrdersModule.class
})
public interface AppComponent {

    void inject(AddEditNoteActivity addEditNoteActivity);

    void inject(AllNotesActivity allNotesActivity);

    void inject(OrdersRemoteDataSource ordersRemoteDataSource);

    void inject(AllOrdersActivity allOrdersActivity);
}
