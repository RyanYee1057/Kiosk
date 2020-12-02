package com.progameming.internproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ViewUtils;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    public RequestQueue requestQueue;

    private CategoryListAdapter categoryListAdapter;
    private ProductListAdapter productListAdapter;
    final ArrayList<productModel> filterList = new ArrayList<>();
    EditText search;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.filterGrid);
        search.addTextChangedListener(this);

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

        gridView = findViewById(R.id.product_grid_view);
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

    private void getAllProduct() {
        String url = "http://pos.api.itmansolution.com/product/getAllProduct";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            if (filterList.equals(new ArrayList<productModel>())) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String product_id = jsonObject.getString("product_id");
                                    String category_id = jsonObject.getString("category_id");
                                    String product_name = jsonObject.getString("product_name");
                                    String selling_price = jsonObject.getString("selling_price");
                                    String category_name = jsonObject.getString("category_name");
                                    String product_pic = jsonObject.getString("img_url");
                                    //Toast.makeText(Product.this, product_pic, Toast.LENGTH_LONG).show();

                                    productModel p = new productModel();
                                    p.setP_id(product_id);
                                    p.setC_id(category_id);
                                    p.setPrice(selling_price);
                                    p.setP_name(product_name);
                                    p.setC_name(category_name);
                                    p.setP_pic(product_pic);
                                    filterList.add(p);
                                }
                            }
                            productListAdapter = new ProductListAdapter(MainActivity.this, filterList);
                            gridView.setAdapter(productListAdapter);
                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
                                    int num = i + 1;
                                    if (productListAdapter.done == null) {
                                        intent.putExtra("x", String.valueOf(num));
                                    } else {
                                        String product_id = productListAdapter.done.get(i).product_id;
                                        intent.putExtra("filter", product_id);
                                    }
                                    startActivity(intent);
                                }
                            });
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
                            gridView.setAdapter(productListAdapter);
                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    public void onAll (View view){
        getAllProduct();
    }

    public void onCart (View view){
        Intent intent = new Intent(MainActivity.this, Cart.class);
        startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        this.productListAdapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable editable) { }
}
