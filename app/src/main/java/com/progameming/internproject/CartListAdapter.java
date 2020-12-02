package com.progameming.internproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;

import java.util.ArrayList;

public class CartListAdapter extends BaseAdapter {
    Context context;
    ArrayList<cartModel> c;
    //JSONArray cartList;

    public CartListAdapter(Context context, ArrayList<cartModel> product) {
        this.context = context;
        this.c = product;
    }

    @Override
    public int getCount() {
        return c.size();
    }

    @Override
    public Object getItem(int position) {
        return c.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_cart, null);

        //JSONObject jsonObject;
        TextView product_id = view.findViewById(R.id.productID);
        TextView product_name = view.findViewById(R.id.productName);
        TextView selling_price= view.findViewById(R.id.productPrice);
        ImageView product_pic = view.findViewById(R.id.p_pic);

        if(c != null) {
            product_id.setText(c.get(position).getP_id());
            selling_price.setText(c.get(position).getPrice());
            product_name.setText(c.get(position).getP_name());
            Glide.with(context).load(c.get(position).getP_pic()).override(160, 150).into(product_pic);
        }
            /*if(cartList != null) {
                jsonObject = cartList.getJSONObject(position);
                product_id.setText(jsonObject.getString("product_id"));
                product_name.setText(jsonObject.getString("product_name"));
                selling_price.setText(jsonObject.getString("selling_price"));
                Glide.with(context).load(jsonObject.getString("img_url")).override(160, 150).into(product_pic);
            }
             */

        return view;
    }
}
