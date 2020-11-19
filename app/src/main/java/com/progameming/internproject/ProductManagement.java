package com.progameming.internproject;

import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductManagement {



    private static ProductManagement sInstance = null;

    public static ProductManagement getInstance(){
        if(sInstance == null){
            sInstance = new ProductManagement();
        }
        return sInstance;
    }

    private JSONArray productList;

    private ProductManagement(){
        productList = null;
    }


    public void loadProduct(RequestQueue requestQueue){
        String url = "http://pos.api.itmansolution.com/product/getAllProduct";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                           productList = response.getJSONArray("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public JSONArray getProductByCategory(String categoryID){
        //TO DO: if you want
        return null;
    }

    public JSONObject getProductDetails(int product_id){
        JSONObject jsonObject = null;
        for(int i = 0; i < productList.length(); i++){
            try {
                JSONObject temp = productList.getJSONObject(i);
                if(temp.getInt("product_id") == product_id){
                    jsonObject = temp;
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

}
