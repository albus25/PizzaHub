package com.example.pizzahub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewOrder extends OptionMenuAct {
    ListView lst;
    JSONArray jsonArray;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<HashMap<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        setTitle("View Orders");

        lst = findViewById(R.id.lst);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String,String> map = arrayList.get(position);

                if(map.get("status").equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewOrder.this);
                    builder.setMessage("Are you sure to confirm Order?");
                    builder.setPositiveButton("Yeah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URIs.Set_Status,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);

                                            if(jsonObject.getInt("success") == 1)
                                            {
                                                Toast.makeText(ViewOrder.this, "Order sent to Master Chef", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(ViewOrder.this,ViewOrder.class));
                                            }
                                            else
                                            {
                                                Toast.makeText(ViewOrder.this,"Not Sent",Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            Toast.makeText(ViewOrder.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(ViewOrder.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String,String> params = new HashMap<>();
                                    params.put("orderID",map.get("orderID"));

                                    return params;
                                }
                            };
                            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                        }
                    });
                    builder.setNegativeButton("Naah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(ViewOrder.this, "Mission failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else
                {
                    Toast.makeText(ViewOrder.this, "Order is already confirmed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URIs.View_Orders,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        jsonArray = jsonObject.getJSONArray("orders");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            HashMap<String,String> map = new HashMap<String, String>();
                            map.put("orderID",jsonObject1.getString("orderID"));
                            map.put("tableNo",jsonObject1.getString("tableNo"));
                            map.put("customerName",jsonObject1.getString("customerName"));
                            map.put("pizzaCategory",jsonObject1.getString("pizzaCategory"));
                            map.put("pizzaSize",jsonObject1.getString("pizzaSize"));
                            map.put("quantity",jsonObject1.getString("quantity"));
                            map.put("price",jsonObject1.getString("price"));
                            map.put("status",jsonObject1.getString("status"));

                            arrayList.add(map);
                        }
                        lst.setAdapter(new CustomAdapter(ViewOrder.this,arrayList));
                    } catch (JSONException e) {
                        Toast.makeText(ViewOrder.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ViewOrder.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
