package com.progameming.internproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    TextView item, tolPrice, noItem;
    ConstraintLayout bil;
    JSONArray cartArray;
    private ArrayList<cartModel> c ;
    //ArrayList<cartModel> c = new ArrayList<>();
    private CartListAdapter cAdapter;

    String shoppingID;
    double totalPrice;
    int numStock, totalItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        c = ProductManagement.getCart();

        for (int i = 0; i < c.size(); i++) {
            c.get(i).selected = false;
        }

        final ListView listView = findViewById(R.id.cartList);
        cAdapter = new CartListAdapter(Cart.this, c, true);
        //cAdapter = new CartListAdapter(c, getLayoutInflater(), true);
        listView.setAdapter(cAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                cartModel selectedProduct = c.get(position);
                selectedProduct.selected = !selectedProduct.selected;
                //selectedProduct.selected = !selectedProduct.selected;

                cAdapter.notifyDataSetInvalidated();

            }
        });

        Button removeButton = (Button) findViewById(R.id.id_delete);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Loop through and remove all the products that are selected
                // Loop backwards so that the remove works correctly
                for (int i = c.size() - 1; i >= 0; i--) {

                    if (c.get(i).selected) {
                        c.remove(i);
                    }
                }
                cAdapter.notifyDataSetChanged();
            }
        });

        bil = findViewById(R.id.bill);
        noItem = (TextView) findViewById(R.id.no);
        item = (TextView) findViewById(R.id.numSubtotal);
        tolPrice = (TextView) findViewById(R.id.SubTotalPrice);
    }

    public void onBack(View view){
        Intent intent = new Intent(Cart.this, MainActivity.class);
        startActivity(intent);
    }

}