package com.kapil.ecomm.data;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
