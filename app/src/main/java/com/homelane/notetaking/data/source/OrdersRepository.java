package com.homelane.notetaking.data.source;

import android.support.annotation.NonNull;

import com.homelane.notetaking.networkresponses.AllOrdersResponse;

import java.util.List;

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

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param ordersDataSource  the device storage data source
     * @return the {@link OrdersRepository} instance
     */
    public static OrdersRepository getInstance(
            OrdersDataSource ordersDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new OrdersRepository(ordersDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(OrdersDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
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
}
