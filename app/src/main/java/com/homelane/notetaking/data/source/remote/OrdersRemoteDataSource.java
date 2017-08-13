package com.homelane.notetaking.data.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.homelane.notetaking.MyApplication;
import com.homelane.notetaking.data.source.OrdersDataSource;
import com.homelane.notetaking.data.source.local.NotesLocalDataSource;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by kapilbakshi on 13/08/17.
 */

public class OrdersRemoteDataSource implements OrdersDataSource {

    private static OrdersRemoteDataSource INSTANCE;

    @Inject
    Retrofit retrofit;

    @Override
    public void getOrdersResponse(@NonNull LoadOrdersCallback callback) {
        MyApplication.getComponent().inject(this);
        NetworkApis networkApis = retrofit.create(NetworkApis.class);
        callback.onGetOrdersResponse(networkApis.getOrders());
    }

    public static OrdersRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrdersRemoteDataSource();
        }
        return INSTANCE;
    }
}
