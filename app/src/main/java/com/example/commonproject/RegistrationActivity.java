package com.example.commonproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EditText tokenInput = findViewById(R.id.tokenInput);
        Button registrationButton = findViewById(R.id.registrationButton);
        sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);

        registrationButton.setOnClickListener(v -> {
            String token = tokenInput.getText().toString();
            APIClient apiClient = new APIClient();
            apiClient.makeGetRequest("/users/", new ApiCallback() {
                @Override
                public void onSuccess(String response) {
                    Gson gson = new Gson();
                    Type userListType = new TypeToken<List<User>>(){}.getType();
                    List<User> users = gson.fromJson(response, userListType);
                    for (User user : users) {
                        if (user.username.equals(token) && token != null){
                            registerUser();
                        }
                    }
                }

                @Override
                public void onFailure(IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    private void registerUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_registered", true);
        editor.apply();

        // Переход к MainActivity
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}