package com.ispcapp.gymapp;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.android.volley.VolleyError;
import com.ispcapp.gymapp.api.ApiRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

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
    iniciarSesion(mail, pass);
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

    private void iniciarSesion(String correo, String passWord) {

        // Llamar a la API para iniciar sesión
        Log.e("Login", "Intentando iniciar sesión...");
        apiRequest.login(correo, passWord, new ApiRequest.ApiCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                loginAttempts = 0; // Resetear intentos al iniciar sesión correctamente
                sharedPreferences.edit().putInt("login_attempts", loginAttempts).remove("block_timestamp").apply(); // Guardar intentos y eliminar bloqueo
                // Procesar la respuesta y continuar el flujo exitoso
                Log.e("try onsuccess", response.toString());
                try {
                    String accessToken = response.getString("access");
                    //saveAccessToken(accessToken);
                    obtenerUsuario(accessToken);
                    /*JSONObject user = response.getJSONObject("user");
                    String first_name = user.getString("first_name");
                    String token = response.getString("token");
                    int id = user.getInt("id");*/
                    // Guardar el nombre y el token
                   // saveUserData(id, first_name, token);
                  /*  Intent volverInicio = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(volverInicio);
                    Toast.makeText(LoginActivity.this, "Sesión iniciada", Toast.LENGTH_SHORT).show();*/
                } catch (JSONException e) {
                    Log.e("Login", "Se produjo un error", e);
                    Log.e("catch", response.toString());
                    Toast.makeText(LoginActivity.this, "Se produjo un error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("Login Error", Objects.requireNonNull(error.getMessage()));
                loginAttempts++; // Incrementar contador al fallar
                sharedPreferences.edit().putInt("login_attempts", loginAttempts).apply(); // Guardar intentos
                Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
                    sharedPreferences.edit().putLong("block_timestamp", System.currentTimeMillis()).apply();
                    Toast.makeText(LoginActivity.this, "Demasiados intentos fallidos. Tu cuenta está bloqueada por 1 minuto.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Toast.makeText(this, "Procesando...", Toast.LENGTH_SHORT).show();
    }

    private void obtenerUsuario(String accessToken) {
        apiRequest.getUserData(accessToken, new ApiRequest.ApiCallback(){
            @Override
            public void onSuccess(JSONObject response){
                try {
                    // Ahora puedes acceder a los datos del usuario, por ejemplo:
                    String firstName = response.getString("first_name");
                    int userId = response.getInt("id");
                    Toast.makeText(LoginActivity.this, "Sesión iniciada", Toast.LENGTH_SHORT).show();
                    // Guardar el nombre y los datos del usuario
                    saveUserData(userId, firstName, accessToken);

                    // Navegar a la siguiente pantalla
                    Intent volverInicio = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(volverInicio);

                } catch (JSONException e) {
                    Log.e("Login", "Error al obtener los datos del usuario", e);
                    Toast.makeText(LoginActivity.this, "Se produjo un error al obtener los datos del usuario.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(VolleyError error) {
                Log.e("Login Error", Objects.requireNonNull(error.getMessage()));
                loginAttempts++; // Incrementar contador al fallar
                sharedPreferences.edit().putInt("login_attempts", loginAttempts).apply(); // Guardar intentos
                Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
                    sharedPreferences.edit().putLong("block_timestamp", System.currentTimeMillis()).apply();
                    Toast.makeText(LoginActivity.this, "Demasiados intentos fallidos. Tu cuenta está bloqueada por 1 minuto.", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }


    private void saveUserData(int id, String firstName, String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("first_name", firstName); // Guarda el nombre del usuario
        editor.putString("jwt_token", token);
        editor.putBoolean("is_logged_in", true);
        editor.putInt("id", id);
        editor.apply();
    }



}