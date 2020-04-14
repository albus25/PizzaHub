package com.example.pizzahub;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton getInstance;
    private RequestQueue requestQueue;
    private static Context context;

    public VolleySingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if(requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }

    public static synchronized VolleySingleton getInstance(Context context)
    {
        if(getInstance == null)
        {
            getInstance = new VolleySingleton(context);
        }

        return getInstance;
    }

    public <T> void addToRequestQueue(Request<T> tRequest)
    {
        getRequestQueue().add(tRequest);
    }
}
