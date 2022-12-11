package com.example.bslnd;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bslnd.http.HttpClient;
import com.example.bslnd.http.model.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText email, password;
    String emailHolder, passwordHolder;
    Boolean editTextEmptyHolder;
    boolean loginSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        loginButton = (Button) findViewById(R.id.buttonLogin);

        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setEnabled(false);
                checkEditTextStatus();
                loginFunction();
                loginButton.setEnabled(true);
            }
        });
    }

    public void loginFunction() {
        if (editTextEmptyHolder) {
            callLogin(new Login(emailHolder, passwordHolder));
            //checkFinalResult();
        } else {
            Toast.makeText(LoginActivity.this, "Please Enter UserName or Password.", Toast.LENGTH_SHORT).show();
        }
    }

    private void callLogin(final Login login) {
        String ipAddress = getIntent().getExtras().getString("ipAddress");
        String portNumber = getIntent().getExtras().getString("port");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress + ":" + portNumber)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpClient retrofitAPI = retrofit.create(HttpClient.class);
        Call<String> call = retrofitAPI.login(login);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String responseFromAPI = response.body();
                if(responseFromAPI == "true") {
                    loginSuccess = true;
                    checkFinalResult();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(LoginActivity.this, "Error LoggingIn!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkEditTextStatus() {
        emailHolder = email.getText().toString();
        passwordHolder = password.getText().toString();
        if (TextUtils.isEmpty(emailHolder) || TextUtils.isEmpty(passwordHolder)) {
            editTextEmptyHolder = false;
        } else {
            editTextEmptyHolder = true;
        }
    }

    public void checkFinalResult() {
        if (loginSuccess) {
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.putExtra("ipAddress", getIntent().getExtras().getString("ipAddress"));
            intent.putExtra("port", getIntent().getExtras().getString("port"));
            startActivity(intent);
        } /*else {
            Toast.makeText(LoginActivity.this, "UserName or Password is Wrong, Please Try Again.", Toast.LENGTH_SHORT).show();
        }*/
    }
}