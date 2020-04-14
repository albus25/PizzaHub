package com.example.pizzahub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

public class ViewBill extends AppCompatActivity {
    JSONArray jsonArray;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<HashMap<String, String>>();
    ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);

        final Intent intent = getIntent();
        lst = findViewById(R.id.lstBill);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URIs.View_Bill,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int total = 0;

                        jsonArray = jsonObject.getJSONArray("bill");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            HashMap<String,String> map = new HashMap<String, String>();
                            map.put("orderID",jsonObject1.getString("orderID"));
                            map.put("customerName",jsonObject1.getString("customerName"));
                            map.put("mobileNo",jsonObject1.getString("mobileNo"));
                            map.put("orderdatetime",jsonObject1.getString("orderdatetime"));
                            map.put("pizzaCategory",jsonObject1.getString("pizzaCategory"));
                            map.put("pizzaSize",jsonObject1.getString("pizzaSize"));
                            map.put("quantity",jsonObject1.getString("quantity"));
                            map.put("price",jsonObject1.getString("price"));

                            int price = Integer.parseInt(map.get("price"));
                            total += price;
                            map.put("total", String.valueOf(total));

                            arrayList.add(map);
                        }
                        SimpleAdapter adapter = new SimpleAdapter(ViewBill.this,arrayList,R.layout.billlayout,
                                new String[]{"orderID","customerName","mobileNo","orderdatetime","pizzaCategory","pizzaSize","quantity","total"},
                                new int[]{R.id.textOID,R.id.textCustName,R.id.textMobNo,R.id.textDt,R.id.textPc,R.id.textPs,R.id.textQty,R.id.textTotal});

                        lst.setAdapter(adapter);
                    } catch (JSONException e) {
                        Toast.makeText(ViewBill.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ViewBill.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("customerName",intent.getStringExtra("customerName"));

                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
