package com.kapil.ecomm.data.source.remote;

import android.support.annotation.NonNull;

import com.kapil.ecomm.MyApplication;
import com.kapil.ecomm.data.source.OrdersDataSource;

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
