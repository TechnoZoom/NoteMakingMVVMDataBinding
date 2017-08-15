package com.homelane.notetaking.networkresponses;

import com.homelane.notetaking.data.Order;
import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by kapilbakshi on 13/08/17.
 */

public class AllOrdersResponse {

    private boolean success;
    private String error_message;
    @Json(name = "result")
    private List<Order> orders;

    public AllOrdersResponse(boolean success, String error_message, List<Order> orders) {
        this.success = success;
        this.error_message = error_message;
        this.orders = orders;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getError_message() {
        return error_message;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
