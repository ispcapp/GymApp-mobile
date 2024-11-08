    package com.ispcapp.gymapp;


    import android.annotation.SuppressLint;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.text.Editable;
    import android.text.TextUtils;
    import android.text.TextWatcher;
    import android.util.Log;
    import android.util.Patterns;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import com.android.volley.AuthFailureError;
    import com.android.volley.DefaultRetryPolicy;
    import com.android.volley.Request;
    import com.android.volley.toolbox.JsonObjectRequest;

    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.HashMap;
    import java.util.Map;
    import java.util.Objects;

    public class SignUpActivity extends AppCompatActivity {

        ImageView btnBack;
        EditText mailInput;
        EditText nameInput;
        EditText apellidoInput;
        EditText passwordInput;
        Button signupBtn;

        @SuppressLint({"MissingInflatedId"})
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_sign_up);

            String BASE_URL = "http://192.168.0.14:8000/api/";
            mailInput = findViewById(R.id.editTextTextEmailAddress);
            nameInput = findViewById(R.id.editTextTextUserName);
            apellidoInput = findViewById(R.id.editTextTextUserName);
            passwordInput = findViewById(R.id.editTextTextPassword);
            signupBtn = findViewById(R.id.btn_signup_signup);
            btnBack = findViewById(R.id.btn_back_signup);

            passwordInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String password = s.toString();
                    boolean hasUpperCase = !password.equals(password.toLowerCase());
                    boolean hasLowerCase = !password.equals(password.toUpperCase());
                    boolean hasDigit = password.matches(".*\\d.*");

                    if (hasUpperCase && hasLowerCase && hasDigit) {
                        Toast.makeText(SignUpActivity.this, "Contraseña válida", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Contraseña insegura", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            signupBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    registrarUser();
                }
            });


            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });


            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
            private void registrarUser() {
                String nombre = nameInput.getText().toString().trim();
                String apellido = apellidoInput.getText().toString().trim();
                String mailUser = mailInput.getText().toString().trim();
                String contrasenia = passwordInput.getText().toString().trim();

                if (mailUser.length() < 7 || !Patterns.EMAIL_ADDRESS.matcher(mailUser).matches()) {
                    mailInput.setError("Correo inválido.");
                    return;
                }

                if (TextUtils.isEmpty(nombre)) {
                    nameInput.setError("El nombre es obligatorio");
                    return;

                } else if (TextUtils.isEmpty(mailUser)) {
                    mailInput.setError("El correo electrónico es obligatorio");
                    return;
                } else if (TextUtils.isEmpty(contrasenia)) {
                    passwordInput.setError("Debe ingresar una contraseña");
                    return;
                }

                String urlSignUp = "http://192.168.0.14:8000/api/register/";

                JSONObject jsonBody = new JSONObject();

                try{
                    jsonBody.put("username", mailUser);
                    jsonBody.put("first_name", nombre);
                    jsonBody.put("last_name", apellido);
                    jsonBody.put("email", mailUser);
                    jsonBody.put("password", contrasenia);
                }catch (JSONException e){
                    e.printStackTrace();
                }

                String jsonString = jsonBody.toString();
                Log.d("JSON Request", jsonString);

                int timeoutMs = 100000;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlSignUp, jsonBody,
                        response -> {
                            try{
                                Log.d("SignUpResponse",response.toString());

                                String token = response.getString("token");

                                SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("auth_token", token);
                                editor.putString("username", mailUser);
                                editor.putBoolean("is_logged_in", true);

                                editor.apply();

                                Toast.makeText(this, "Usuario registrado.", Toast.LENGTH_SHORT).show();
                                clearForm();

                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }catch(JSONException e){
                                e.printStackTrace();
                                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                            }

                        },
                        error ->{
                            if(error.networkResponse != null){
                                int statusCode = error.networkResponse.statusCode;
                                if(statusCode == 400){
                                    Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();

                                }else if(statusCode == 404){
                                    Toast.makeText(this, "Problemas de conexión.", Toast.LENGTH_SHORT).show();
                                    Log.d("404: ", Objects.requireNonNull(error.getMessage()));
                                }else{
                                    Toast.makeText(this, "Error: " + statusCode, Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(this, error.getMessage() + "Error final", Toast.LENGTH_SHORT).show();
                                Log.d("SignUpResponse", Objects.requireNonNull(error.getMessage()));
                            }
                        }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError{
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        timeoutMs,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
                VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


            }






        private void clearForm() {
            nameInput.setText("");
            passwordInput.setText("");
            mailInput.setText("");
        }

}