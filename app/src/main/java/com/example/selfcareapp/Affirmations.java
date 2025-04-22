package com.example.selfcareapp;
/*
Affirmation Activity class
Functionality:
    -Retrieves affirmation from Api
    -Display affirmation in TextView
    -Sets up random button to retrieve another affirmation when clicked
    -Caches data for offline use
    -utilizes view binding
    -Uses the AffirmationApiClient class and the Affirmation ApiService Interface

Concepts from class:
    -Activity binding
    -Listeners
        -setOnClickListeners
    -Intents to start new activity
    -Set new text for a TextView based on activity function
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.selfcareapp.databinding.ActivityAffirmationsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Affirmations extends AppCompatActivity {
    ActivityAffirmationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityAffirmationsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        fetchAffirmation();
        //set up random button
        binding.btnRandomAf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchAffirmation();
            }
        });

        //set up lotus to home
        binding.ivLotusAffirmations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Affirmations.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Method to fetch a random affirmation from the api
    private void fetchAffirmation() {
        AffirmationApiService apiService = AffirmationApiClient.getInstance().getApiService();
        Call<AffirmationModel> call = apiService.getRandomAffirmation();

        call.enqueue(new Callback<AffirmationModel>() {
            @Override
            public void onResponse(Call<AffirmationModel> call, Response<AffirmationModel> response) {
                if(response.isSuccessful() && response.body()!=null){
                    AffirmationModel affirmation = response.body();
                    binding.affirmation.setText(affirmation.getAffirmation());

                    //store for offline use
                    saveAffirmationToPrefs(affirmation.getAffirmation());
                }else{
                    //show cached affirmation if available
                    String cachedAffirmation = getAffirmationFromPrefs();
                    if (cachedAffirmation != null && !cachedAffirmation.isEmpty()){
                        binding.affirmation.setText(cachedAffirmation);
                    }else{
                        binding.affirmation.setText("You are a bad ass!");
                    }
                }
            }

            @Override
            public void onFailure(Call<AffirmationModel> call, Throwable t) {
                //show cached affirmation if available
                String cachedAffirmation = getAffirmationFromPrefs();
                if(cachedAffirmation!=null && !cachedAffirmation.isEmpty()){
                    binding.affirmation.setText(cachedAffirmation);
                }else{
                    binding.affirmation.setText("You can do hard things.");
                }
            }
        });
    }

    //get a reference to apps SharedPreferences file, stores affirmation with key "cached_affirmation
    private void saveAffirmationToPrefs(String affirmation){
        SharedPreferences prefs = getPreferences(MODE_PRIVATE); //only the app can retrieve the data
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("cached_affirmation",affirmation);
        editor.apply();
    }

    //retrieves affirmation from the same file with the same key
    private String getAffirmationFromPrefs(){
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        return prefs.getString("cached_affirmation","");
    }

}