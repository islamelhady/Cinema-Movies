package com.elhady.cinema_movies.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.elhady.cinema_movies.R;
import com.elhady.cinema_movies.viewmodel.MostPopularTVShowViewModel;

public class MainActivity extends AppCompatActivity {

    private MostPopularTVShowViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       viewModel = ViewModelProviders.of(this).get(MostPopularTVShowViewModel.class);
        mostPopularTVShow();
    }

    private void mostPopularTVShow() {
        viewModel.getMostPopularTVShow(0).observe(this,mostPopularTVshowResponse ->
                Toast.makeText(getApplicationContext(), "Total Pages"+mostPopularTVshowResponse.getPages()
                        , Toast.LENGTH_SHORT).show()
        );
    }
}