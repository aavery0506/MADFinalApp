package com.example.selfcareapp;

import retrofit2.Call;

import java.util.List;

import retrofit2.http.GET;

public interface AffirmationApiService {
    //Get a random affirmation
    @GET("/")
    Call<AffirmationModel> getRandomAffirmation();

}
