package com.progameming.internproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

        final ArrayList<cartModel> c = ProductManagement.getCart();
        final cartModel cart = new cartModel();
        selectedNo = findViewById(R.id.num);

        Intent intent = getIntent();
        product_id = intent.getIntExtra("product_id",0);
        p_idALL = intent.getStringExtra("x");
        p_idFi = intent.getStringExtra("filter");

        final JSONObject productDetails;
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

            cart.setP_id(productDetails.getString("product_id"));
            cart.setP_name(productDetails.getString("product_name"));
            cart.setPrice(productDetails.getString("selling_price"));
            cart.setP_pic(productDetails.getString("img_url"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button addCartButton = (Button) findViewById(R.id.addCart);
        addCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.setQuantity(String.valueOf(selectedNum));

                c.add(cart);

                if(selectedNum > 1){
                    Toast.makeText(ProductDetailsActivity.this, "Total " + selectedNum + " items", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ProductDetailsActivity.this, "Total 1 item", Toast.LENGTH_LONG).show();

                }
                Toast.makeText(ProductDetailsActivity.this, "Add cart successful!", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        /*if(c.contains(cart)) {
            addCartButton.setEnabled(false);
            addCartButton.setText("Item in Cart");
        }*/
    }

    public void decreaseNum(View view){
        selectedNum = Integer.parseInt(selectedNo.getText().toString());
        selectedNum--;
        if (selectedNum <1)
            selectedNum = 1;
        selectedNo.setText(String.valueOf(selectedNum));
    }

    public void increaseNum(View view){
        selectedNum = Integer.parseInt(selectedNo.getText().toString());
        selectedNum++;
        selectedNo.setText(String.valueOf(selectedNum));
    }

    public void onBack(View view){
        Intent intent = new Intent(ProductDetailsActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
