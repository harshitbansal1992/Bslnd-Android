package com.example.bslnd.http;


import com.example.bslnd.http.model.Login;
import com.example.bslnd.http.model.Sevadar;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface HttpClient {
    /*@POST("sevadar")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    //on below line we are creating a method to post our data.
    Call<Sevadar> createPost(@Body Sevadar dataModal);*/

    @POST("sevadar")
    @Multipart
    @Headers({"Accept: application/json"})
        //on below line we are creating a method to post our data.
    Call<Sevadar> createSevadar(@Part("sevadar") RequestBody sevadar, @Part MultipartBody.Part image);

    @POST("login")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
        //on below line we are creating a method to post our data.
    Call<String> login(@Body Login dataModal);
}