package com.progameming.internproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public RequestQueue requestQueue;

    private CategoryListAdapter categoryListAdapter;
    private ProductListAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        ProductManagement.getInstance().loadProduct(requestQueue);

        ListView listView = findViewById(R.id.category_list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                JSONObject jsonObject = (JSONObject) categoryListAdapter.getItem(i);

                try {
                    getProductByCategory(jsonObject.getString("category_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        listView = findViewById(R.id.product_list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                JSONObject jsonObject = (JSONObject) productListAdapter.getItem(i);

                try {
                    Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
                    intent.putExtra("product_id", jsonObject.getInt("product_id"));
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        getAllCategories();
    }

    private void getAllCategories() {
        String url = "http://pos.api.itmansolution.com/product/getAllCategory";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            categoryListAdapter = new CategoryListAdapter(MainActivity.this, jsonArray);
                            ListView listView = findViewById(R.id.category_list_view);
                            listView.setAdapter(categoryListAdapter);

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

    private void getProductByCategory(String categoryID){
        String url = "http://pos.api.itmansolution.com/product/getProductByCategory/" + categoryID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            productListAdapter = new ProductListAdapter(MainActivity.this, jsonArray);
                            ListView listView = findViewById(R.id.product_list_view);
                            listView.setAdapter(productListAdapter);

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
}
