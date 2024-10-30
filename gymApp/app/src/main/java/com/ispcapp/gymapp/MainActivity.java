package com.ispcapp.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.ispcapp.gymapp.api.ClaseApi;
import com.ispcapp.gymapp.api.ApiClient;
import com.ispcapp.gymapp.models.Clase;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnSignup;
    private ClaseApi claseApi;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos la interfaz API usando el cliente Retrofit singleton
        claseApi = ApiClient.getInstance().create(ClaseApi.class);

        // Ejecutamos una llamada de prueba para obtener las clases
        obtenerClases();


        btnLogin = findViewById(R.id.btn_login_get_started);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignup = findViewById(R.id.btn_signup_get_started);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void obtenerClases() {
        Call<List<Clase>> call = claseApi.getClases();

        call.enqueue(new Callback<List<Clase>>() {
            @Override
            public void onResponse(Call<List<Clase>> call, Response<List<Clase>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Clase> clases = response.body();
                    mostrarClasesEnLog(clases);
                } else {
                    Toast.makeText(MainActivity.this, "Error al obtener las clases", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Clase>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API_ERROR", t.getMessage(), t);
            }
        });


    }

    private void mostrarClasesEnLog(List<Clase> clases) {
        for (Clase clase : clases) {
            Log.d("CLASE", "ID: " + clase.getId() + ", Nombre: " + clase.getNombre());
        }
    }

    }