package com.example.selfcareapp;

import retrofit2.Call;

import java.util.List;

import retrofit2.http.GET;

/*
Interface to set random affirmation for the Api
 */
public interface AffirmationApiService {
    //Get a random affirmation
    @GET("/")
    Call<AffirmationModel> getRandomAffirmation();

}
