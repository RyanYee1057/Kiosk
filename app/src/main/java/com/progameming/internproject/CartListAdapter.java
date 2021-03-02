package com.progameming.internproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CartListAdapter extends BaseAdapter {
    Context context;
    private ArrayList<cartModel> c;
    private LayoutInflater mInflater;
    private boolean mShowCheckbox;

    public CartListAdapter(Context context, ArrayList<cartModel> list, boolean showCheckbox) {
        this.context = context;
        this.c = list;
        //mInflater = inflater;
        mShowCheckbox = showCheckbox;
    }

    public CartListAdapter(ArrayList<cartModel> list, LayoutInflater Inflater, boolean showCheckbox) {
        this.c = list;
        mInflater = Inflater;
        mShowCheckbox = showCheckbox;
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
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewItem item;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_cart, null);

            item = new ViewItem();

            //JSONObject jsonObject;
            item.product_id = view.findViewById(R.id.productID);
            item.product_name = view.findViewById(R.id.productName);
            item.selling_price = view.findViewById(R.id.productPrice);
            item.product_pic = view.findViewById(R.id.p_pic);
            item.quantity = view.findViewById(R.id.quantityNo);
            item.checkBox = view.findViewById(R.id.checkBox);

            item.num = view.findViewById(R.id.num);
            item.plus = view.findViewById(R.id.button_plus);
            item.minus = view.findViewById(R.id.button_minus);

            view.setTag(item);
        }else{
            item = (ViewItem) view.getTag();
        }

        if(c != null) {
            item.product_id.setText(c.get(position).getP_id());
            item.selling_price.setText("RM " + c.get(position).getPrice());
            item.product_name.setText(c.get(position).getP_name());
            Glide.with(context).load(c.get(position).getP_pic()).override(160, 150).into(item.product_pic);
            item.quantity.setText(c.get(position).getQuantity());
            item.num.setText(c.get(position).getQuantity());

            if(c.get(position).isSelected())
                item.checkBox.setChecked(true);
            else
                item.checkBox.setChecked(false);

        }

        item.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    item.checkBox.setChecked(true);
                    c.get(position).setSelected(true);
                }
                else{
                    item.checkBox.setChecked(false);
                    c.get(position).setSelected(false);
                }
            }
        });

        item.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedNum = Integer.parseInt(item.num.getText().toString());
                selectedNum++;
                item.num.setText(String.valueOf(selectedNum));
                item.quantity.setText(String.valueOf(selectedNum));
                c.get(position).setQuantity(String.valueOf(selectedNum));
                calculate();
            }
        });

        item.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedNum = Integer.parseInt(item.num.getText().toString());
                selectedNum--;
                if (selectedNum <1)
                    selectedNum = 1;
                item.num.setText(String.valueOf(selectedNum));
                item.quantity.setText(String.valueOf(selectedNum));
                c.get(position).setQuantity(String.valueOf(selectedNum));
               calculate();
            }
        });

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

    public void calculate(){
       // LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //LayoutInflater.from(Cart);
        //setContentView();
        //View view = inflater.inflate(R.layout.activity_cart, null);

        //TextView price = view.findViewById(R.id.showPrice);
        double p = 0.00;
        if(!c.isEmpty()) {
            for (int i = 0; i < c.size(); i++) {
                p += (Double.parseDouble(c.get(i).getPrice()) * Double.parseDouble(c.get(i).getQuantity()));

            }
            //price.setText("RM" + String.format("%.2f", p));
        }else{
            String pp = "RM 0";
            p = 0.00;
            //price.setText(pp);
        }
        Cart.getInstance().calculation(p);
        //cart.calculation(p);
        //Toast.makeText(context, "calculate: " + p, Toast.LENGTH_SHORT).show();
    }

    private class ViewItem {
        TextView product_id;
        TextView product_name;
        TextView selling_price;
        ImageView product_pic;
        TextView quantity;
        CheckBox checkBox;
        ImageButton plus, minus;
        TextView num;
    }
}
