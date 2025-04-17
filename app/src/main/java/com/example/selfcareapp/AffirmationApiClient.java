package com.example.selfcareapp;



import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AffirmationApiClient {
    private static final String BASE_URL = "https://www.affirmations.dev/";
    private static AffirmationApiClient instance;
    private Retrofit retrofit;

    private AffirmationApiClient(){

        //initialize Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
    //Singleton pattern
    public static synchronized AffirmationApiClient getInstance(){
        if (instance == null){
            instance = new AffirmationApiClient();
        }
        return instance;
    }

    public AffirmationApiService getApiService(){
        return retrofit.create(AffirmationApiService.class);
    }
}
