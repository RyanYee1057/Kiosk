package com.progameming.internproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends BaseAdapter implements Filterable {
    Context context;
    JSONArray productList;
    ArrayList<productModel> filterList, pList, done;
    ItemFilter fi;

    public ProductListAdapter(Context context, JSONArray product) {
        this.context = context;
        this.productList = product;
    }

    public ProductListAdapter(Context context, ArrayList<productModel> product) {
        this.context = context;
        this.filterList = product;
        this.pList = product;
    }

    @Override
    public int getCount() {
        if(productList != null) {
            return productList.length();
        }
        else {
            return pList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        try {
            if(productList != null) {
                return productList.getJSONObject(position);

            }
            else {
                return pList.get(position);
            }
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
            view = inflater.inflate(R.layout.item_product, null);

        try {
            if(pList != null) {
                TextView product_id = view.findViewById(R.id.p_id);
                TextView selling_price = view.findViewById(R.id.price);
                TextView product_name = view.findViewById(R.id.p_name);
                TextView category_name = view.findViewById(R.id.c_name);
                ImageView product_pic = view.findViewById(R.id.p_pic);

                product_id.setText(pList.get(position).getP_id());
                selling_price.setText("RM " + pList.get(position).getPrice());
                product_name.setText(pList.get(position).getP_name());
                category_name.setText(pList.get(position).getC_name());

                Glide.with(context).load(pList.get(position).getP_pic()).override(160, 150).into(product_pic);
            }
            else{
                JSONObject jsonObject = productList.getJSONObject(position);

                TextView textView = view.findViewById(R.id.p_id);
                textView.setText(jsonObject.getString("product_id"));

                textView = view.findViewById(R.id.p_name);
                textView.setText(jsonObject.getString("product_name"));

                textView = view.findViewById(R.id.price);
                textView.setText("RM " + jsonObject.getString("selling_price"));

                textView = view.findViewById(R.id.c_name);
                textView.setText(jsonObject.getString("category_name"));

                ImageView pic = view.findViewById(R.id.p_pic);
                Glide.with(context).load(jsonObject.getString("img_url")).override(200, 200).into(pic);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        if(fi == null){
            fi = new ItemFilter();
        }
        return fi;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if(constraint!=null && constraint.length()>0) {
                constraint = constraint.toString().toLowerCase();
                done = new ArrayList<>();

                for(int i=0; i<filterList.size();i++){
                    if(filterList.get(i).getP_name().toLowerCase().contains(constraint)){
                        productModel p = new productModel(filterList.get(i).getP_id(), filterList.get(i).getP_name(),
                                filterList.get(i).getPrice(),filterList.get(i).getC_name(), filterList.get(i).getP_pic());
                        done.add(p);
                    }
                }
                results.values = done;
                results.count = done.size();
            }
            else {
                results.values = filterList;
                results.count = filterList.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //productList = (JSONArray) results.values;
            pList = (ArrayList<productModel>) results.values;
            notifyDataSetChanged();
        }
    }
}