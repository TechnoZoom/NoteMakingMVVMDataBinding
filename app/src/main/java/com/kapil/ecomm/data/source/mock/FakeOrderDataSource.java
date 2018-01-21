package com.kapil.ecomm.data.source.mock;

import android.support.annotation.NonNull;

import com.kapil.ecomm.data.Order;
import com.kapil.ecomm.data.Product;
import com.kapil.ecomm.data.source.OrdersDataSource;
import com.kapil.ecomm.networkresponses.AllOrdersResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Observable;

/**
 * Created by kapilbakshi on 14/08/17.
 */

public class FakeOrderDataSource implements OrdersDataSource {

    private static Observable<AllOrdersResponse> ALL_ORDER_RESPONSE_OBSERVABLE;

    private static AllOrdersResponse ALL_ORDER_RESPONSE;

    private static FakeOrderDataSource INSTANCE;

    private static AllOrdersResponse getAllOrderResponse() {
        if(ALL_ORDER_RESPONSE == null) {
            createAll_Order_Response();
        }
        return ALL_ORDER_RESPONSE;
    }

    public static void createALL_ORDER_RESPONSE_OBSERVABLE() {
        createAll_Order_Response();
        ALL_ORDER_RESPONSE_OBSERVABLE = Observable.just(getAllOrderResponse());
    }

    public static void createAll_Order_Response() {
        String errorMessage = null;
        boolean success = true;
        List<Order> orderList = new ArrayList<Order>();
        ALL_ORDER_RESPONSE = new AllOrdersResponse(success, errorMessage, orderList);
    }

    @Override
    public void getOrdersResponse(@NonNull LoadOrdersCallback callback) {
        callback.onGetOrdersResponse(getAllOrderResponseObservable());
    }

    public static Observable<AllOrdersResponse> getAllOrderResponseObservable() {
        return ALL_ORDER_RESPONSE_OBSERVABLE;
    }

    public static FakeOrderDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeOrderDataSource();
        }
        return INSTANCE;
    }

    public void create_Exception_Error_Observable(String exceptionMessage) {
        reCreateAll_Order_Response();
        ALL_ORDER_RESPONSE_OBSERVABLE = Observable.<AllOrdersResponse>error(new NullPointerException(exceptionMessage));
    }

    public void createOrdersObservable(String...statuses) {
        reCreateAll_Order_Response();
        for(String status:statuses) {
            Order order = createOrderBasedOnStatus(status, new Random().nextInt(Integer.MAX_VALUE));
            addOrders(order);
        }
        ALL_ORDER_RESPONSE_OBSERVABLE = Observable.just(getAllOrderResponse());
    }

    public void createAllOrderResponseWithServerErrorObservable(String errorMessage) {
        reCreateAll_Order_Response();
        addErrorToAllOrdersResponse(errorMessage);
        toggleSuccess(false);
        ALL_ORDER_RESPONSE_OBSERVABLE = Observable.just(getAllOrderResponse());
    }

    public void addErrorToAllOrdersResponse(String errorMessage) {
        getAllOrderResponse().setError_message(errorMessage);
    }

    public void toggleSuccess(boolean success) {
        getAllOrderResponse().setSuccess(success);
    }

    public void addOrders(Order... orders) {
        if (orders != null) {
            for (Order order : orders) {
                getAllOrderResponse().getOrders().add(order);
            }
        }
    }


    public void reCreateAll_Order_Response() {
        createAll_Order_Response();
    }



    public void removeOrder(Order order) {
        getAllOrderResponse().getOrders().remove(order);
    }

    public void removeAllOrders() {
        getAllOrderResponse().getOrders().clear();
    }


    public Order createOrderBasedOnStatus(String status,int id) {
        return new Order(id,status,new Product("Pixel","mobile",65000));
    }

}
