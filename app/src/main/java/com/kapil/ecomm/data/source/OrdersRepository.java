package com.kapil.ecomm.data.source;

import android.support.annotation.NonNull;

import com.kapil.ecomm.networkresponses.AllOrdersResponse;

import rx.Observable;

/**
 * Created by kapilbakshi on 13/08/17.
 */

public class OrdersRepository implements OrdersDataSource {

    private static OrdersRepository INSTANCE = null;
    private final OrdersDataSource ordersDataSource;

    private OrdersRepository(
            @NonNull OrdersDataSource ordersDataSource) {
        this.ordersDataSource = ordersDataSource;
    }


    public static OrdersRepository getInstance(
            OrdersDataSource ordersDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new OrdersRepository(ordersDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getOrdersResponse(@NonNull final OrdersDataSource.LoadOrdersCallback callback) {
        ordersDataSource.getOrdersResponse(new OrdersDataSource.LoadOrdersCallback() {
            @Override
            public void onGetOrdersResponse(Observable<AllOrdersResponse> ordersResponseObservable) {
                callback.onGetOrdersResponse(ordersResponseObservable);
            }
        });
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
