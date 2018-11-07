package com.example.goett.service;

/**
 * Created by goett on 08/03/18.
 */


import com.example.goett.model.PlanetaResults;
import com.example.goett.model.Planetas;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface APIInterface {

    @GET("/api/planets/{page}")
    Call<Planetas> doGetListResources(@Path("page") int page);


    @GET("/api/planets/")
    Call<PlanetaResults> doGetListResourcesCount();

    @Headers( "Content-Type: application/json" )
    @POST("/api/planets")
    Call<Planetas> savePost(@Body RequestBody planeta);

    @Headers( "Content-Type: application/json" )
    @PUT("/api/planets/")
    Call<Planetas> update(@Body RequestBody planeta);

    @DELETE("/api/planets/{id}")
    Call<Planetas> deleteGist(@Path("id") int id);

    /**@POST("/api/planets/")
    @FormUrlEncoded
    Call<Planetas> savePost(@Field("name") String nameValue,
                            @Field("climate") String climateValue,
                            @Field("terrain") String terrenoValue);*/
}
