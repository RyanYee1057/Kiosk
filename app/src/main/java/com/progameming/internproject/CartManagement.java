package com.progameming.internproject;

import org.json.JSONObject;

import java.util.List;

public class CartManagement {
    private static CartManagement sInstance = null;

    public static CartManagement getInstance(){
        if(sInstance == null){
            sInstance = new CartManagement();
        }
        return sInstance;
    }

    private List<JSONObject> cartItemList;

    private CartManagement(){
        cartItemList = null;
    }

    public void addProduct(){

    };

    public void removeProduct(){

    };

    public void changeProductQty(){

    };

    public void calculateTotalPrice(){

    };

    public void checkOut(){

    };

}
