package com.homelane.notetaking.data.source.mock;

import android.support.annotation.NonNull;

import com.homelane.notetaking.constants.OrderLifeCycleConstants;
import com.homelane.notetaking.data.Order;
import com.homelane.notetaking.data.Product;
import com.homelane.notetaking.data.source.OrdersDataSource;
import com.homelane.notetaking.networkresponses.AllOrdersResponse;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by kapilbakshi on 14/08/17.
 */

public class FakeOrderDataSource implements OrdersDataSource {

    private static Observable<AllOrdersResponse> ALL_ORDER_RESPONSE_OBSERVABLE;

    public static AllOrdersResponse ALL_ORDER_RESPONSE;

    public static AllOrdersResponse getAllOrderResponse() {
        if(ALL_ORDER_RESPONSE == null) {
            createAll_Order_Response();
        }
        return ALL_ORDER_RESPONSE;
    }

    public static void createALL_ORDER_RESPONSE_OBSERVABLE() {
        createAll_Order_Response();
        ALL_ORDER_RESPONSE_OBSERVABLE = Observable.just(getAllOrderResponse());
    }

    public static Observable<AllOrdersResponse> getAllOrderResponseObservable() {
        return ALL_ORDER_RESPONSE_OBSERVABLE;
    }

    public static FakeOrderDataSource INSTANCE;

    @Override
    public void getOrdersResponse(@NonNull LoadOrdersCallback callback) {
        callback.onGetOrdersResponse(getAllOrderResponseObservable());
    }

    public static FakeOrderDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeOrderDataSource();
        }
        return INSTANCE;
    }

    public void create_Exception_Error_Observable(String exceptionMessage) {
        ALL_ORDER_RESPONSE_OBSERVABLE = Observable.<AllOrdersResponse>error(new NullPointerException(exceptionMessage));
    }

    public void createOrder_Delivery_Cancelled_And_Received_Observable() {
        reCreateAll_Order_Response();
        Order receivedOrder = createOrderBasedOnStatus(OrderLifeCycleConstants.STATUS_ORDER_RECEIVED,12346);
        Order deliveryOrder = createOrderBasedOnStatus(OrderLifeCycleConstants.STATUS_ORDER_OUT_FOR_DELIVERY,127886);
        Order cancelledOrder = createOrderBasedOnStatus(OrderLifeCycleConstants.STATUS_ORDER_CANCELLED,9889899);
        addOrders(receivedOrder,cancelledOrder,deliveryOrder);
        ALL_ORDER_RESPONSE_OBSERVABLE = Observable.just(getAllOrderResponse());
    }

    public void createAllOrderResponseWithServerErrorObservable(String errorMessage) {
        reCreateAll_Order_Response();
        addErrorToAllOrdersResponse(errorMessage);
        toggleSuccess(false);
        ALL_ORDER_RESPONSE_OBSERVABLE = Observable.just(getAllOrderResponse());
    }

    public static void createAll_Order_Response() {
        String errorMessage = null;
        boolean success = true;
        List<Order> orderList = new ArrayList<Order>();
        ALL_ORDER_RESPONSE = new AllOrdersResponse(success,errorMessage,orderList);
    }

    public void reCreateAll_Order_Response() {
        createAll_Order_Response();
    }

    public void addOrder(Order order) {
        getAllOrderResponse().getOrders().add(order);
    }

    public void addOrders(Order... orders) {
        if (orders != null) {
            for (Order order : orders) {
                getAllOrderResponse().getOrders().add(order);
            }
        }
    }

    public void removeOrder(Order order) {
        getAllOrderResponse().getOrders().remove(order);
    }

    public void removeAllOrders() {
        getAllOrderResponse().getOrders().clear();
    }

    public void addErrorToAllOrdersResponse(String errorMessage) {
        getAllOrderResponse().setError_message(errorMessage);
    }

    public void toggleSuccess(boolean success) {
        getAllOrderResponse().setSuccess(success);
    }

    public Order createOrderBasedOnStatus(String status,int id) {
        return new Order(id,status,new Product("Pixel","mobile",65000));
    }

}
