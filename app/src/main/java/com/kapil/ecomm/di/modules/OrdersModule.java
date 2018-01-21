package com.kapil.ecomm.di.modules;

import com.kapil.ecomm.data.source.OrdersRepository;
import com.kapil.ecomm.data.source.remote.OrdersRemoteDataSource;

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
