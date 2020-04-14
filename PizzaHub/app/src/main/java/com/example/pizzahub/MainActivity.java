package com.example.pizzahub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btnLog;
    EditText txtwno,txtwpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login");

        btnLog = findViewById(R.id.btnLogin);
        txtwno = findViewById(R.id.txtwno);
        txtwpass = findViewById(R.id.txtwpass);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(txtwno.getText().toString()))
                {
                    txtwno.setError("MobileNo is required");
                    txtwno.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(txtwpass.getText().toString()))
                {
                    txtwpass.setError("Password is required");
                    txtwpass.requestFocus();
                    return;
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URIs.Waiter_Login,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if(jsonObject.getInt("success") == 1)
                                {
                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("mobileNo",txtwno.getText().toString());
                                    editor.commit();

                                    Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(MainActivity.this,TakeOrder.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "Login not done", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<>();
                        params.put("mobileNo",txtwno.getText().toString());
                        params.put("password",txtwpass.getText().toString());

                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        loadSharedPreference();
    }

    public void loadSharedPreference()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String MobileNo = sharedPreferences.getString("mobileNo","");
        if(MobileNo != null && !MobileNo.equals(""))
        {
            Intent intent = new Intent(this,TakeOrder.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Please Login first", Toast.LENGTH_SHORT).show();
        }
    }
}
