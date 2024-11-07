package com.ispcapp.gymapp.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.DefaultRetryPolicy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiRequest {

    private final RequestQueue requestQueue;
    private static final String BASE_URL = "http://127.0.0.1:8000/";

    public ApiRequest(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

   /* public void login (String username, String password, final ApiCallback callback){
        String url = BASE_URL + "login/"
    }*/

}


