package com.homelane.notetaking.constants;

/**
 * Created by kapilbakshi on 14/08/17.
 */

public class OrderLifeCycleConstants {

    public static final String STATUS_ORDER_CANCELLED = "Cancelled";
    public static final String STATUS_ORDER_RECEIVED = "Order Received";
    public static final String STATUS_ORDER_OUT_FOR_DELIVERY = "Out for delivery";

    public static final String[] ORDER_STATUSES_ARRAY = new String[] {
            STATUS_ORDER_OUT_FOR_DELIVERY, STATUS_ORDER_CANCELLED, STATUS_ORDER_RECEIVED
    };
}
