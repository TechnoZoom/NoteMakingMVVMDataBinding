package com.kapil.ecomm.data.source;

import android.support.annotation.NonNull;

import com.kapil.ecomm.networkresponses.AllOrdersResponse;

import rx.Observable;

/**
 * Created by kapilbakshi on 13/08/17.
 */

public interface OrdersDataSource {

    interface LoadOrdersCallback {

        void onGetOrdersResponse(Observable<AllOrdersResponse> ordersResponseObservable);
    }

    void getOrdersResponse(@NonNull OrdersDataSource.LoadOrdersCallback callback);

}
