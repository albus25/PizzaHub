package com.example.pizzahub;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomAdapter extends BaseAdapter {
    private final Activity context;
    private final ArrayList<HashMap<String,String>> arrayList;

    public CustomAdapter(Activity context, ArrayList<HashMap<String,String>> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(R.layout.customlists,null,true);

        TextView t1 = convertView.findViewById(R.id.lblOID);
        TextView t2 = convertView.findViewById(R.id.lblTNo);
        TextView t3 = convertView.findViewById(R.id.lblCustNm);
        TextView t4 = convertView.findViewById(R.id.lblPizzaCat);
        TextView t5 = convertView.findViewById(R.id.lblPizzaSize);
        TextView t6 = convertView.findViewById(R.id.lblQty);
        TextView t7 = convertView.findViewById(R.id.lblPrice);

        t1.setText("OrderID : "+arrayList.get(position).get("orderID"));
        t2.setText("Table No : "+arrayList.get(position).get("tableNo"));
        t3.setText("Customer Name : "+arrayList.get(position).get("customerName"));
        t4.setText("Pizza Category : "+arrayList.get(position).get("pizzaCategory"));
        t5.setText("Pizza Size : "+arrayList.get(position).get("pizzaSize"));
        t6.setText("Quantity : "+arrayList.get(position).get("quantity"));
        t7.setText("Price : "+arrayList.get(position).get("price"));

        return convertView;
    }
}
