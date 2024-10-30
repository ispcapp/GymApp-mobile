package com.ispcapp.gymapp.api;


import com.ispcapp.gymapp.models.Token;
import com.ispcapp.gymapp.models.UserCredentials;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("api/token/")
    Call<Token> login(@Body UserCredentials credentials);
}