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
import org.json.JSONArray;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    TextView price;
    private TextView numQ;
    ConstraintLayout bil;
    JSONArray cartArray;
    private ArrayList<cartModel> c ;
    //ArrayList<cartModel> c = new ArrayList<>();
    private CartListAdapter cAdapter;

    Spinner spinner;
    String shoppingID;
    double totalPrice;
    String numStock;
    int totalItem;
    int selectedNum = 1;

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
                for (int i = c.size() - 1; i >= 0; i--) {

                    if (c.get(i).selected) {
                        c.remove(i);
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

        // fix stock quantity
        numQ = findViewById(R.id.num);

        //spinner = (Spinner) findViewById(R.id.stockSpinner);
        //spinner;
        //numStock;

    }

    /*public void decreaseNum(View view){
        for (int i = 0; i < c.size(); i++) {
            selectedNum = Integer.parseInt(c.get(i).getQuantity());
            selectedNum--;
            if (selectedNum < 1)
                selectedNum = 1;
            //c.get(i).setQuantity(String.valueOf(selectedNum));
            numQ.setText(String.valueOf(selectedNum));
            //selectedNo.setText(String.valueOf(selectedNum));
        }
    }

    public void increaseNum(View view){
        for (int i = 0; i < c.size(); i++) {
            selectedNum = Integer.parseInt(c.get(i).getQuantity());   //
            selectedNum++;
            System.out.println(selectedNum);
            numQ.setText(String.valueOf(selectedNum));
            //c.get(i).setQuantity(String.valueOf(selectedNum));
            //selectedNo.setText(String.valueOf(selectedNum));
        }
    }

     */

    //quantity need to change manually, able change and provide a receipt

    public void onBack(View view){
        Intent intent = new Intent(Cart.this, MainActivity.class);
        startActivity(intent);
    }

}