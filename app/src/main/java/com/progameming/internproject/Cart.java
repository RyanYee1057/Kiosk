package com.progameming.internproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    TextView price;

    JSONArray cartArray;
    private ArrayList<cartModel> c ;
    //ArrayList<cartModel> c = new ArrayList<>();
    private CartListAdapter cAdapter;

    Spinner spinner;
    double totalPrice;
    String numStock;
    int selectedNum = 1;
    private TextView selectedNo;
    TextView num;
    private static Cart instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        instance = this;

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

                numStock = selectedProduct.getStock();

                cAdapter.notifyDataSetInvalidated();

            }
        });

        Button removeButton = (Button) findViewById(R.id.id_delete);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Loop through and remove all the products that are selected
                // Loop backwards so that the remove works correctly
                //Toast.makeText(Cart.this, String.valueOf(c.size()) , Toast.LENGTH_SHORT).show();
                for (int i = c.size() - 1; i >= 0; i--) {

                    if (c.get(i).isSelected()) {
                        c.remove(i);
                        //Toast.makeText(Cart.this, "delete" + i , Toast.LENGTH_SHORT).show();
                    }
                    double p = 0.00;
                    for (int a = 0; a < c.size(); a++) {
                        p += (Double.parseDouble(c.get(a).getPrice()) * Double.parseDouble(c.get(a).getQuantity()));
                    }
                    price.setText("RM" + String.format("%.2f", p));
                }
                cAdapter.notifyDataSetChanged();
            }
        });

        price = (TextView) findViewById(R.id.showPrice);
        double p = 0.00;
        if(!c.isEmpty()) {
            for (int i = 0; i < c.size(); i++) {
                p += (Double.parseDouble(c.get(i).getPrice()) * Double.parseDouble(c.get(i).getQuantity()));

            }
            price.setText("RM" + String.format("%.2f", p));
        }else{
            String pp = "RM 0";
            price.setText(pp);
        }

        num = findViewById(R.id.num);
    }

    public static Cart getInstance() {
        return instance;
    }


    public void calculation(double totalPrice){
        TextView price = (TextView) findViewById(R.id.showPrice);
        Toast.makeText(this, "haha", Toast.LENGTH_SHORT).show();
        price.setText(String.format("RM%.2f", totalPrice));
        //Toast.makeText(this, "calculate: " + p, Toast.LENGTH_SHORT).show();
    }

/*
    public void onAdd(View view){
            calculation();
        Toast.makeText(this, "haha", Toast.LENGTH_SHORT).show();
    }

    public void onMinus(View view){
        calculation();
    }

     */


    //quantity need to change manually, able to control
    //while manually quantity, checkBox will stop function
    //provide a receipt
    //@Override


    public void onBack(View view){
        Intent intent = new Intent(Cart.this, MainActivity.class);
        startActivity(intent);
    }

    public void onNext(View view){
        Intent intent = new Intent(Cart.this, MainActivity.class);
        startActivity(intent);
    }

}