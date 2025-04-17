package com.example.selfcareapp;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AffirmationApiClient {
    private static final String BASE_URL = "https://www.affirmations.dev/";
    private static AffirmationApiClient instance;
    private AffirmationApiService aptService;

    private AffirmationApiClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aptService = retrofit.create(AffirmationApiService.class);
    }

    public static synchronized AffirmationApiClient getInstance(){
        if (instance == null){
            instance = new AffirmationApiClient();
        }
        return instance;
    }

    public AffirmationApiService getApiService(){
        return aptService;
    }
}
