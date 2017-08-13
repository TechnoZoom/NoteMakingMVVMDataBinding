package com.homelane.notetaking.data;

/**
 * Created by kapilbakshi on 13/08/17.
 */

public class Product {

    private String name;
    private String type;
    private int price;

    public Product(String name, String type, int price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }
}
