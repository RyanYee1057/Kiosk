package com.progameming.internproject;

import java.util.ArrayList;

public class productModel extends ArrayList<productModel> {
    String product_id;
    String category_id;
    String selling_price;
    String product_name, category_name, product_pic;

    public productModel(String p_id, String p_name, String price, String c_name, String p_pic){
        this.product_name = p_name;
        this.category_name = c_name;
        this.product_id = p_id;
        this.selling_price = price;
        this.product_pic = p_pic;
    }

    public productModel() {
    }

    public String getP_id(){
        return product_id;
    }

    public void setP_id(String product_id){
        this.product_id = product_id;
    }

    public String getC_id(){
        return category_id;
    }

    public void setC_id(String category_id){
        this.category_id  = category_id;
    }

    public String getPrice(){
        return selling_price;
    }

    public void setPrice(String selling_price){
        this.selling_price = selling_price;
    }

    public String getP_name(){
        return product_name;
    }

    public void setP_name(String product_name){
        this.product_name = product_name;
    }

    public String getC_name(){
        return category_name;
    }

    public void setC_name(String category_name){
        this.category_name = category_name;
    }

    public void setP_pic(String product_pic){
        this.product_pic = product_pic;
    }

    public String getP_pic(){ return product_pic;}

}
