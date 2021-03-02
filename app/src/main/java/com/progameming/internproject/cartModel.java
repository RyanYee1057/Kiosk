package com.progameming.internproject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;
import java.util.Objects;

public class cartModel extends ArrayList<cartModel> {
    String product_id, selling_price, product_name, product_pic;
    String quantity, stock_qty;
    boolean selected;

    public cartModel(String p_id, String p_name, String price, String p_pic, String q){
        this.product_id = p_id;
        this.product_name = p_name;
        this.selling_price = price;
        this.product_pic = p_pic;
        this.quantity = q;
    }

    public cartModel(String p_id, String p_name, String price, String p_pic){
        this.product_id = p_id;
        this.product_name = p_name;
        this.selling_price = price;
        this.product_pic = p_pic;
    }

    public cartModel() {

    }

    public String getP_id(){
        return product_id;
    }

    public void setP_id(String product_id){
        this.product_id = product_id;
    }

    public String getP_name(){
        return product_name;
    }

    public void setP_name(String product_name){
        this.product_name = product_name;
    }

    public String getPrice(){
        return selling_price;
    }

    public void setPrice(String selling_price){
        this.selling_price = selling_price;
    }

    public String getP_pic(){ return product_pic;}

    public void setP_pic(String product_pic){
        this.product_pic = product_pic;
    }

    public String getQuantity() { return quantity;}

    public void setQuantity(String quantity){this.quantity = quantity;}

    public String getStock() { return stock_qty;}

    public void setStock(String stock_qty){this.stock_qty = stock_qty;}

}
