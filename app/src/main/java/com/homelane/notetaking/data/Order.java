package com.homelane.notetaking.data;

/**
 * Created by kapilbakshi on 13/08/17.
 */

public class Order {

    private int id;
    private String status;
    private Product product;

    public Order(int id, String status, Product product) {
        this.id = id;
        this.status = status;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Product getProduct() {
        return product;
    }
}
