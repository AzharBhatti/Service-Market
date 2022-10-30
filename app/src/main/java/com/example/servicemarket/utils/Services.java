package com.example.servicemarket.utils;

import com.example.servicemarket.model.LoginRequest;
import com.example.servicemarket.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Services {

    @Headers("Content-Type: application/json")
    @POST("login")
    public Call<LoginResponse<Object>> login(@Body LoginRequest body);

}
