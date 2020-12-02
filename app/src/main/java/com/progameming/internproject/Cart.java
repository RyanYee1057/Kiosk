package com.progameming.internproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    int product_qty, p_id;
    String p_idALL, p_idFi;

    TextView item, tolPrice, noItem;
    ConstraintLayout bil;
    ScrollView list;
    ListView listView;

    JSONArray cartArray;
    final ArrayList<cartModel> c = new ArrayList<>();

    public RequestQueue requestQueue;
    public MySingleton mySingleton;

    String shoppingID;
    double totalPrice;
    int numStock, totalItem;

    public Cart(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        product_qty = intent.getIntExtra("product_qty",0);
        p_id = intent.getIntExtra("p_id",0);
        p_idALL = intent.getStringExtra("p_idALL");
        p_idFi = intent.getStringExtra("p_idFi");

        System.out.println(product_qty);
        System.out.println(p_id);
        System.out.println(p_idALL);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        ProductManagement.getInstance().loadProduct(requestQueue);

        listView = findViewById(R.id.cartList);

        String url = "http://pos.api.itmansolution.com/product/getAllProduct";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            cartArray = response.getJSONArray("data");
                            getCartByID();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        System.out.println(jsonObjectRequest);
        requestQueue.add(jsonObjectRequest);
        //mySingleton.addToRequestQueue(jsonObjectRequest);

        list = (ScrollView) findViewById(R.id.listCart);

        bil = findViewById(R.id.bill);
        noItem = (TextView) findViewById(R.id.no);
        item = (TextView) findViewById(R.id.numSubtotal);
        tolPrice = (TextView) findViewById(R.id.SubTotalPrice);
    }

    private void getCartByID(){
        CartListAdapter cAdapter;
        if(p_idALL != null){
            for(int i = 0; i < cartArray.length(); i++){
                try {
                    JSONObject jsonObject = cartArray.getJSONObject(i);

                    if(jsonObject.getString("product_id").contains(p_idALL)){

                    String product_id = jsonObject.getString("product_id");
                    String product_name = jsonObject.getString("product_name");
                    String selling_price = jsonObject.getString("selling_price");
                    String product_pic = jsonObject.getString("img_url");

                    cartModel cM = new cartModel();
                    cM.setP_id(product_id);
                    cM.setPrice(selling_price);
                    cM.setP_name(product_name);
                    cM.setP_pic(product_pic);
                    c.add(cM);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cAdapter = new CartListAdapter(Cart.this, c);
            listView.setAdapter(cAdapter);
        }
        else if(p_idFi != null){
            for(int i = 0; i < cartArray.length(); i++){
                try {
                    JSONObject jsonObject = cartArray.getJSONObject(i);
                    if(jsonObject.getString("product_id").contains(p_idFi)){
                        String product_id = jsonObject.getString("product_id");
                        String product_name = jsonObject.getString("product_name");
                        String selling_price = jsonObject.getString("selling_price");
                        String product_pic = jsonObject.getString("img_url");

                        cartModel cM = new cartModel();
                        cM.setP_id(product_id);
                        cM.setPrice(selling_price);
                        cM.setP_name(product_name);
                        cM.setP_pic(product_pic);
                        c.add(cM);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cAdapter = new CartListAdapter(Cart.this, c);
            listView.setAdapter(cAdapter);
        } else{
            for(int i = 0; i < cartArray.length(); i++){
                try {
                    JSONObject jsonObject = cartArray.getJSONObject(i);
                    if(jsonObject.getInt("product_id") == p_id){
                        String product_id = jsonObject.getString("product_id");
                        String product_name = jsonObject.getString("product_name");
                        String selling_price = jsonObject.getString("selling_price");
                        String product_pic = jsonObject.getString("img_url");

                        cartModel cM = new cartModel();
                        cM.setP_id(product_id);
                        cM.setPrice(selling_price);
                        cM.setP_name(product_name);
                        cM.setP_pic(product_pic);
                        c.add(cM);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cAdapter = new CartListAdapter(Cart.this, c);
            listView.setAdapter(cAdapter);
        }
    }

}