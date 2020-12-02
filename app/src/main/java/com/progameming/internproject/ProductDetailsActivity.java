package com.progameming.internproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetailsActivity extends AppCompatActivity {

    int product_id;
    String p_idALL;
    String p_idFi;
    int selectedNum;
    private TextView selectedNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        selectedNo= findViewById(R.id.num);

        Intent intent = getIntent();
        product_id = intent.getIntExtra("product_id",0);
        p_idALL = intent.getStringExtra("x");
        p_idFi = intent.getStringExtra("filter");
        System.out.println(product_id);

        JSONObject productDetails;
        if(p_idALL != null){
            productDetails = ProductManagement.getInstance().getProductDetails(p_idALL);
        }
        else if(p_idFi != null){
            productDetails = ProductManagement.getInstance().getProductDetails(p_idFi);
        } else{
            productDetails = ProductManagement.getInstance().getProductDetails(product_id);
        }

        TextView id = findViewById(R.id.productID);
        TextView name = findViewById(R.id.productName);
        TextView price = findViewById(R.id.productPrice);
        ImageView image = findViewById(R.id.productPic);
        TextView stock = findViewById(R.id.quanlityStock);
        TextView desProduct = findViewById(R.id.id_description);
        TextView detail = findViewById(R.id.id_detail);

        try {
            id.setText(productDetails.getString("product_id"));
            name.setText(productDetails.getString("product_name"));
            price.setText("RM " + productDetails.getString("selling_price"));
            Glide.with(ProductDetailsActivity.this).load(productDetails.getString("img_url")).override(200, 200).into(image);
            desProduct.setText(productDetails.getString("description"));
            detail.setText(productDetails.getString("ingredients"));
            stock.setText(productDetails.getString("stock_qty"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void decreaseNum(View view){
        selectedNum = Integer.parseInt(selectedNo.getText().toString());
        selectedNum--;
        if (selectedNum <0)
            selectedNum = 0;
        selectedNo.setText(String.valueOf(selectedNum));
    }

    public void increaseNum(View view){
        selectedNum = Integer.parseInt(selectedNo.getText().toString());
        selectedNum++;
        selectedNo.setText(String.valueOf(selectedNum));
    }

    public void onAddCart(View view){
        Intent intent = new Intent(ProductDetailsActivity.this, Cart.class);
        intent.putExtra("product_qty", selectedNum);

        if(p_idALL != null){
            intent.putExtra("p_idALL", p_idALL);
        }
        else if(p_idFi != null){
            intent.putExtra("p_idFi", p_idFi);
        } else{
            intent.putExtra("p_id", product_id);
        }

        startActivity(intent);
    }

    public void onCart(View view){
        Intent intent = new Intent(ProductDetailsActivity.this, Cart.class);
        startActivity(intent);
    }
}
