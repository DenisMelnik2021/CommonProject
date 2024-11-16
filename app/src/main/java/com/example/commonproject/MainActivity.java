package com.example.commonproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {
    private ItemAdapter adapter;
    private SharedPreferences sharedPreferences;
    private APIClient apiClient = new APIClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);

        if (!isRegistered()) {
            startActivity(new Intent(this, RegistrationActivity.class));
            finish(); // Закрываем MainActivity, чтобы пользователь не мог вернуться к нему без регистрации
        }

        TextView textView = findViewById(R.id.textView);
        textView.setText("Список заданий");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> texts = new ArrayList<>();
        List<String> links = new ArrayList<>();

        apiClient.makeGetRequest("/tasks/", new ApiCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                Type taskListType = new TypeToken<List<Task>>(){}.getType();
                List<Task> tasks = gson.fromJson(response, taskListType);
                for (Task task : tasks) {
                    if (!task.status){
                        texts.add(task.title);
                        links.add(task.link);
                    }
                }
            }
            @Override
            public void onFailure(IOException e) {
                throw new RuntimeException(e);
            }
        });

        adapter = new ItemAdapter(this, texts, links, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        String link = adapter.links.get(position);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }
    private boolean isRegistered() {
        return sharedPreferences.getBoolean("is_registered", false);
    }
}