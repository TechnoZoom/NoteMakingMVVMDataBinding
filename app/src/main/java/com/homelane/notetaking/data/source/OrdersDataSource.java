package com.homelane.notetaking.data.source;

import android.support.annotation.NonNull;

import com.homelane.notetaking.data.Note;
import com.homelane.notetaking.data.Order;
import com.homelane.notetaking.networkresponses.AllOrdersResponse;

import java.util.List;

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
