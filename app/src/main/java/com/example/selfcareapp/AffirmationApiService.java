package com.example.selfcareapp;

import retrofit2.Call;

import java.util.List;

import retrofit2.http.GET;

public interface AffirmationApiService {
    //Get a random affirmation
    @GET("affirmation")
    Call<AffirmationModel> getRandomAffirmation();

    //get all affirmations
    @GET("affirmations")
    Call<List<AffirmationModel>> getAllAffirmations();
}
