package com.homelane.notetaking.di;

import com.homelane.notetaking.addnote.AddEditNoteActivity;
import com.homelane.notetaking.data.source.remote.OrdersRemoteDataSource;
import com.homelane.notetaking.di.modules.NetworkModule;
import com.homelane.notetaking.di.modules.NotesModule;
import com.homelane.notetaking.di.modules.OrdersModule;
import com.homelane.notetaking.notes.AllNotesActivity;
import com.homelane.notetaking.orderlifecycle.AllOrdersActivity;

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
