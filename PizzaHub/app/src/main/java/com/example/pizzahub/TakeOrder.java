package com.example.pizzahub;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TakeOrder extends OptionMenuAct {
    Button btnTakeOrder,btnDate,btnTime;
    EditText txttno,txtcn,txtmno,txtdt;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    String d,t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_order);
        setTitle("Take Order");

        btnTakeOrder = findViewById(R.id.btnTakeOrder);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        txttno = findViewById(R.id.txtTableNo);
        txtcn = findViewById(R.id.txtCustomerName);
        txtmno = findViewById(R.id.txtMobNo);
        txtdt = findViewById(R.id.txtdatetime);
        txtdt.setEnabled(false);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                d = year + "-" + (month+1) + "-" + dayOfMonth;
                txtdt.setText(d + " ");
            }
        },year,month,day);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                t = hourOfDay + ":" + minute;
                txtdt.append(t + ":00");
            }
        },hour,minute,true);
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

        btnTakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(txttno.getText().toString()))
                {
                    txttno.setError("Table no is required");
                    txttno.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(txtcn.getText().toString()))
                {
                    txtcn.setError("Customer Name is required");
                    txtcn.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(txtmno.getText().toString()))
                {
                    txtmno.setError("Mobile No is required");
                    txtmno.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(txtdt.getText().toString()))
                {
                    txtdt.setError("Date Time is required");
                    txtdt.requestFocus();
                    return;
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URIs.Take_Order,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if(jsonObject.getInt("success") == 1)
                                {
                                    Intent intent = new Intent(TakeOrder.this,OrderPizza.class);
                                    intent.putExtra("customerName",txtcn.getText().toString());
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(TakeOrder.this, "Please fill details properly!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(TakeOrder.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(TakeOrder.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<>();
                        params.put("tableNo",txttno.getText().toString());
                        params.put("customerName",txtcn.getText().toString());
                        params.put("mobileNo",txttno.getText().toString());
                        params.put("orderdatetime",txtdt.getText().toString());

                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,TakeOrder.class));
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
