package com.progameming.internproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductListAdapter extends BaseAdapter {
    Context context;
    JSONArray categoryList;

    public ProductListAdapter(Context context, JSONArray product) {
        this.context = context;
        this.categoryList = product;

    }

    @Override
    public int getCount() {
        return categoryList.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return categoryList.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_category, null);


        try {
            JSONObject jsonObject = categoryList.getJSONObject(position);

            TextView textView = view.findViewById(R.id.id_text);
            textView.setText(jsonObject.getString("product_id"));

            textView = view.findViewById(R.id.name_text);
            textView.setText(jsonObject.getString("product_name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

}