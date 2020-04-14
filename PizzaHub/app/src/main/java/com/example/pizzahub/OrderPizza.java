package com.example.pizzahub;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class OrderPizza extends OptionMenuAct {
    Button btnPizza,btnCalculate;
    Spinner spPizza,spSize;
    EditText txtqty,txtprice;
    TextView tv;
    JSONArray jsonArray;

    String[] pizza = new String[]{"Veg Pizza","Burger Pizza","Italian Pizza","Cheese Pizza"};
    String[] size = new String[]{"Small","Large","Jumbo"};
    String s,p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pizza);

        final Intent intent = getIntent();

        btnPizza = findViewById(R.id.btnPizza);
        btnCalculate = findViewById(R.id.btnCalculate);
        tv = findViewById(R.id.lblOrderID);

        spPizza = findViewById(R.id.spPizza);
        ArrayAdapter<String> pizzaAdp = new ArrayAdapter<String>(OrderPizza.this,android.R.layout.simple_list_item_1,pizza);
        spPizza.setAdapter(pizzaAdp);

        spSize = findViewById(R.id.spSize);
        ArrayAdapter<String> sizeAdp = new ArrayAdapter<String>(OrderPizza.this,android.R.layout.simple_list_item_1,size);
        spSize.setAdapter(sizeAdp);

        txtqty = findViewById(R.id.txtQuantity);
        txtprice = findViewById(R.id.txtPrice);
        txtprice.setEnabled(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URIs.Order_ID,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        jsonArray = jsonObject.getJSONArray("orderID");

                        for (int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            HashMap<String,String> map = new HashMap<String, String>();
                            map.put("orderID",jsonObject1.getString("orderID"));

                            tv.setText(map.get("orderID"));
                            tv.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(OrderPizza.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(OrderPizza.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = spPizza.getSelectedItem().toString();
                s = spSize.getSelectedItem().toString();
                int qty = Integer.parseInt(txtqty.getText().toString());
                if(p.equals("Veg Pizza"))
                {
                    if(s.equals("Small"))
                    {
                        txtprice.setText(Integer.toString(qty * 100));
                    }
                    else if(s.equals("Large"))
                    {
                        txtprice.setText(Integer.toString(qty * 200));
                    }
                    else if(s.equals("Jumbo"))
                    {
                        txtprice.setText(Integer.toString(qty * 500));
                    }
                }
                else if(p.equals("Burger Pizza"))
                {
                    if(s.equals("Small"))
                    {
                        txtprice.setText(Integer.toString(qty * 150));
                    }
                    else if(s.equals("Large"))
                    {
                        txtprice.setText(Integer.toString(qty * 360));
                    }
                    else if(s.equals("Jumbo"))
                    {
                        txtprice.setText(Integer.toString(qty * 490));
                    }
                }
                else if(p.equals("Italian Pizza"))
                {
                    if(s.equals("Small"))
                    {
                        txtprice.setText(Integer.toString(qty * 200));
                    }
                    else if(s.equals("Large"))
                    {
                        txtprice.setText(Integer.toString(qty * 450));
                    }
                    else if(s.equals("Jumbo"))
                    {
                        txtprice.setText(Integer.toString(qty * 680));
                    }
                }
                else if(p.equals("Cheese Pizza"))
                {
                    if(s.equals("Small"))
                    {
                        txtprice.setText(Integer.toString(qty * 120));
                    }
                    else if(s.equals("Large"))
                    {
                        txtprice.setText(Integer.toString(qty * 290));
                    }
                    else if(s.equals("Jumbo"))
                    {
                        txtprice.setText(Integer.toString(qty * 410));
                    }
                }
            }
        });

        btnPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(TextUtils.isEmpty(txtqty.getText().toString()))
            {
                txtqty.setError("Please fill quantity");
                txtqty.requestFocus();
                return;
            }
            if(TextUtils.isEmpty(txtprice.getText().toString()))
            {
                txtprice.setError("Please select pizza or size");
                txtprice.requestFocus();
                return;
            }

            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URIs.Order_Pizza,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if(jsonObject.getInt("success") == 1)
                                {
                                    Intent intent1 = new Intent(OrderPizza.this,ViewOrder.class);
                                    startActivity(intent1);

                                    Toast.makeText(OrderPizza.this, "Order Placed", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(OrderPizza.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(OrderPizza.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<>();
                        params.put("orderID",tv.getText().toString());
                        params.put("pizzaCategory",spPizza.getSelectedItem().toString());
                        params.put("pizzaSize",spSize.getSelectedItem().toString());
                        params.put("quantity",txtqty.getText().toString());
                        params.put("price",txtprice.getText().toString());

                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest1);
            }
        });
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
