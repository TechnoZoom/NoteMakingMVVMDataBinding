package com.homelane.notetaking.data.source.mock;

import android.support.annotation.NonNull;

import com.homelane.notetaking.data.Order;
import com.homelane.notetaking.data.source.OrdersDataSource;
import com.homelane.notetaking.data.source.remote.OrdersRemoteDataSource;
import com.homelane.notetaking.networkresponses.AllOrdersResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by kapilbakshi on 14/08/17.
 */

public class FakeOrderDataSource implements OrdersDataSource {

    private List<Order> ordersList;

    public static FakeOrderDataSource INSTANCE;

    @Override
    public void getOrdersResponse(@NonNull LoadOrdersCallback callback) {
        callback.onGetOrdersResponse(Observable.<AllOrdersResponse>error(new NullPointerException("dwedw")));
    }

    public static FakeOrderDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeOrderDataSource();
        }
        return INSTANCE;
    }
}
