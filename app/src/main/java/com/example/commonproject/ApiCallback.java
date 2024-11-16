package com.example.commonproject;

import java.io.IOException;

public interface ApiCallback { 
    void onSuccess(String response);
    void onFailure(IOException e);
}
