package com.example.shophere;

public class product_ShoppingCart {
    String product_id, userID, shoppingCart_id;
    int quantity;
    public product_ShoppingCart(){

    }
    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

}
