package com.progameming.internproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

public class ProductDetailsActivity extends AppCompatActivity {

    int product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Intent intent = getIntent();
        product_id = intent.getIntExtra("product_id",0);

        JSONObject productDetails = ProductManagement.getInstance().getProductDetails(product_id);
        TextView textView = findViewById(R.id.product_details_text);
        textView.setText(productDetails.toString());
    }
}
