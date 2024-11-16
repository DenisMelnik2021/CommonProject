package com.example.commonproject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIClient {
    private static final String BASE_URL = "http://10.0.2.2:8000/api"; // Замените на ваш базовый URL
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    public void makeGetRequest(String endpoint, final ApiCallback callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + endpoint)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    callback.onSuccess(responseBody);
                } else {
                    callback.onFailure(new IOException("Unexpected code " + response));
                }
            }
        });
    }

    public void makePostRequest(String endpoint, String jsonBody, final ApiCallback callback) {
        RequestBody body = RequestBody.create(jsonBody, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + endpoint)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    callback.onSuccess(responseBody);
                } else {
                    callback.onFailure(new IOException("Unexpected code " + response));
                }
            }
        });
    }
}
