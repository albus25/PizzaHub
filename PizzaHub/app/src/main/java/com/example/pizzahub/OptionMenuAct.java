package com.example.pizzahub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class OptionMenuAct extends AppCompatActivity {
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<HashMap<String, String>>();
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.mo:
                final Dialog dialog = new Dialog(OptionMenuAct.this);
                dialog.setContentView(R.layout.customerlists);
                dialog.setTitle("Select Customer");

                final ListView lst = dialog.findViewById(R.id.lstCustomer);
                lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String,String> map = arrayList.get(position);
                        Intent intent = new Intent(OptionMenuAct.this,OrderPizza.class);
                        intent.putExtra("customerName",map.get("customerName"));
                        startActivity(intent);
                    }
                });

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URIs.Get_Customers,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                jsonArray = jsonObject.getJSONArray("customerName");
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    HashMap<String,String> map = new HashMap<String,String>();
                                    map.put("customerName",jsonObject1.getString("customerName"));

                                    arrayList.add(map);
                                }
                                SimpleAdapter adapter = new SimpleAdapter(OptionMenuAct.this,arrayList,R.layout.customers,
                                        new String[]{"customerName"},
                                        new int[]{R.id.LblcustomerName});
                                lst.setAdapter(adapter);

                            } catch (JSONException e) {
                                Toast.makeText(OptionMenuAct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(OptionMenuAct.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<>();
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                dialog.show();
                return true;

            case R.id.vo:
                Intent intent = new Intent(this,ViewOrder.class);
                startActivity(intent);
                return true;

            case R.id.vb:
                final Dialog dialog1 = new Dialog(OptionMenuAct.this);
                dialog1.setContentView(R.layout.customerlists);
                dialog1.setTitle("Select Customer");

                final ListView lst1 = dialog1.findViewById(R.id.lstCustomer);
                lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String,String> map = arrayList.get(position);
                        Intent intent = new Intent(OptionMenuAct.this,ViewBill.class);
                        intent.putExtra("customerName",map.get("customerName"));
                        startActivity(intent);
                    }
                });

                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URIs.Get_Customers,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    jsonArray = jsonObject.getJSONArray("customerName");
                                    for(int i=0;i<jsonArray.length();i++)
                                    {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        HashMap<String,String> map = new HashMap<String,String>();
                                        map.put("customerName",jsonObject1.getString("customerName"));

                                        arrayList.add(map);
                                    }
                                    SimpleAdapter adapter = new SimpleAdapter(OptionMenuAct.this,arrayList,R.layout.customers,
                                            new String[]{"customerName"},
                                            new int[]{R.id.LblcustomerName});
                                    lst1.setAdapter(adapter);

                                } catch (JSONException e) {
                                    Toast.makeText(OptionMenuAct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(OptionMenuAct.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<>();
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest1);
                dialog1.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
