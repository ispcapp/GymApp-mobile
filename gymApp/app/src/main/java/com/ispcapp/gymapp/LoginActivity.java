package com.ispcapp.gymapp;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.ispcapp.gymapp.api.ApiRequest;

public class LoginActivity extends AppCompatActivity {

    ImageView btnBack;
    Button btnLogin;
    TextView tv_forgot_pass;

    private static final String SITE_KEY = "conseguir key";

    private EditText useremail;
    private EditText password;
    private ApiRequest apiRequest;
    private SharedPreferences sharedPreferences;
    private int loginAttempts = 0;
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final long BLOCK_DURATION = 60 * 1000;



    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        loginAttempts = sharedPreferences.getInt("login_attempts", 0);

        long blockTimeStamp = sharedPreferences.getLong("block_timestamp",0);
        if(blockTimeStamp != 0 && System.currentTimeMillis() - blockTimeStamp < BLOCK_DURATION){
            Toast.makeText(this, "Se ha bloqueado la cuenta momentáneamente. Vuelve a internar más tarde.",Toast.LENGTH_SHORT).show();
        } else if(blockTimeStamp != 0 && System.currentTimeMillis()- blockTimeStamp <= BLOCK_DURATION){
            sharedPreferences.edit().remove("block_timestamp").apply();
            loginAttempts = 0;
            sharedPreferences.edit().putInt("login_attempts", loginAttempts).apply();
        }

        useremail = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword3);
        Button login_btn = findViewById(R.id.btn_login_login);

        apiRequest = new ApiRequest(this);

        login_btn.setOnClickListener(view -> {
            validarForm();
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.btn_back_login);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       /* btnLogin = findViewById(R.id.btn_login_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });*/

        // Manejador de Fragments
    /*    FragmentManager fragmentManager = getSupportFragmentManager();

        tv_forgot_pass = findViewById(R.id.ResetPasswordFragment);
        tv_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, ForgotPasswordFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });*/

        //Para agregar toogle ver contraseña

    }


private void validarForm() {

    long blockTimestamp = sharedPreferences.getLong("block_timestamp", 0);
    if (blockTimestamp != 0 && System.currentTimeMillis() - blockTimestamp < BLOCK_DURATION) {
        Toast.makeText(this, "Usuario temporalmente bloqueado.", Toast.LENGTH_SHORT).show();
        return;
    } else if (blockTimestamp != 0 && System.currentTimeMillis() - blockTimestamp >= BLOCK_DURATION) {
        sharedPreferences.edit().remove("block_timestamp").apply();
        loginAttempts = 0;
        sharedPreferences.edit().putInt("login_attempts", loginAttempts).apply();
    }

    if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
        sharedPreferences.edit().putLong("block_timestamp", System.currentTimeMillis()).apply();
        Toast.makeText(this, "Errores reiterados. Cuenta bloqueada temporalmente.", Toast.LENGTH_SHORT).show();
        return;
    }


    String mail = useremail.getText().toString().trim();
    String pass = password.getText().toString().trim();

    if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass)) {
        Toast.makeText(LoginActivity.this, "Por favor completar todos los campos.", Toast.LENGTH_SHORT).show();

        if (TextUtils.isEmpty(mail)) {
            useremail.setError("Ingrese correctamente su correo electrónico.");
        }

        if (TextUtils.isEmpty(pass)) {
            password.setError("Escriba su contraseña.");
        }
        return;
    }

    if (mail.length() < 7 || !Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
        useremail.setError("Correo inválido.");
        return;
    }

    if (pass.length() < 8 || pass.length() > 16) {
        password.setError("La contraseña con errores.");
        return;
    }

    // para agregar
  /*  HCaptchaConfig hCaptchaConfig = HCaptchaConfig.builder()
            .siteKey(SITE_KEY)
            .locale("es")
            .build();

    HCaptcha.getClient(this).verifyWithHCaptcha(hCaptchaConfig)
            .addOnSuccessListener(hCaptchaTokenResponse -> {
                // Verificación exitosa
                String hCaptchaToken = hCaptchaTokenResponse.getTokenResult(); // Obtener el token del response
                Log.d("hCaptcha", "Verificación exitosa. Token: " + hCaptchaToken);
                iniciarSesion(dni, contrasenia, hCaptchaToken);
            })
            .addOnFailureListener(e -> {
                // Error en la verificación
                if (e instanceof HCaptchaException) {
                    Log.e("hCaptcha", "Verificación fallida. Error: " + e.getMessage());
                }
            });*/
}
}