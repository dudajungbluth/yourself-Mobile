package com.example.duda;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {
    String BASE_URL = "http://192.168.1.10/yourself-project/api/";
    @GET("products/product")
    Call<List<Oculos>> getProducts();
    @GET("products/product/{id}")
    Call<List<Oculos>> getProductById(@Path("id") int id);
    @POST("users/login")
    Call<User> loginUser(@Body RequestBody requestBody);
    @POST("users/")
    Call<User> createUser(@Body RequestBody requestBody);
    @DELETE("products/delete-product/{id}")
    Call<Oculos> deleteOc(@Path("id") int id);

}
