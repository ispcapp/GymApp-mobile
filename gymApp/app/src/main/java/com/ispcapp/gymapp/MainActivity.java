package com.ispcapp.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnLogin, btnSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Comprobar si el usuario está logueado
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);
        String first_name = sharedPreferences.getString("first_name", "Usuario");
        int id = sharedPreferences.getInt("id", -1);


        btnLogin = findViewById(R.id.btn_login_get_started);
        btnLogin.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));

        btnSignup = findViewById(R.id.btn_signup_get_started);
        btnSignup.setOnClickListener(v -> startActivity((new Intent(MainActivity.this, SignUpActivity.class))));

    }
}