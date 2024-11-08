package com.ispcapp.gymapp.api;

import static  android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.DefaultRetryPolicy;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class ApiService {
    private final RequestQueue requestQueue;
    private static final String BASE_URL = "http://192.168.0.14:8000/api/";

    public ApiService(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getReservas(String token, final ApiCallback callback){
        String urlGetReservas = BASE_URL+"reservas/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlGetReservas, null, response -> {
            try {
                callback.onSuccess(response);
            }catch (Exception e){
                Log.e("ApiService", "Error procesando la respuesta: ", e);
                callback.onError(new VolleyError("Error procesando la respuesta"));
            }
        },
                error -> {
                    String errorMessage;

                    if (error.networkResponse != null) {
                        switch (error.networkResponse.statusCode) {
                            case 404:
                                errorMessage = "No se encontraron turnos.";
                                break;
                            case 500:
                                errorMessage = "Error del servidor. Intenta más tarde.";
                                break;
                            case 401:
                                errorMessage = "Credenciales de autenticación no proporcionadas.";
                                break;
                            default:
                                errorMessage = "Error desconocido.";
                                break;
                        }
                    } else {
                        errorMessage = "Error de conexión. Verifica tu red.";
                    }

                    Log.e("VolleyError", errorMessage);
                    callback.onError(new VolleyError(errorMessage));
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Token " + token);
                return headers;
            }
        };

        int socketTimeout = 3000;
        DefaultRetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);

        requestQueue.add(jsonArrayRequest);

        }


    public interface ApiCallback {
        void onSuccess(JSONArray response); // Para la respuesta de los turnos
        void onError(VolleyError error); // Para manejar errores
    }
}
