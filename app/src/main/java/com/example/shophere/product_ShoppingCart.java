package com.example.shophere;

public class product_ShoppingCart {
    String product_id, shoppingCart_id;
    int quantity;
    double product_price;
    public product_ShoppingCart(){

    }
    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getShoppingCart_id() {
        return shoppingCart_id;
    }

    public void setShoppingCart_id(String shoppingCart_id) {
        this.shoppingCart_id = shoppingCart_id;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }
}
