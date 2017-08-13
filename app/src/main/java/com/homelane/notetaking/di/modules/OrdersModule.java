package com.homelane.notetaking.di.modules;

import com.homelane.notetaking.MyApplication;
import com.homelane.notetaking.data.source.NotesRepository;
import com.homelane.notetaking.data.source.OrdersRepository;
import com.homelane.notetaking.data.source.local.NotesLocalDataSource;
import com.homelane.notetaking.data.source.remote.OrdersRemoteDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kapilbakshi on 14/08/17.
 */

@Module
public class OrdersModule {

    @Provides
    @Singleton
    public OrdersRepository providesNotesRepository() {
        return OrdersRepository.getInstance(
                OrdersRemoteDataSource.getInstance());
    }

}
