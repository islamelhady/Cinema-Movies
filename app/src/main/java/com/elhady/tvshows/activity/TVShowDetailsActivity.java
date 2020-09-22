package com.elhady.tvshows.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.elhady.tvshows.R;
import com.elhady.tvshows.adapters.ImageSliderAdapter;
import com.elhady.tvshows.databinding.ActivityTvShowDetailsBinding;
import com.elhady.tvshows.models.TVShowDetailsResponse;
import com.elhady.tvshows.viewmodel.TVShowDetailsViewModel;

public class TVShowDetailsActivity extends AppCompatActivity {

    private ActivityTvShowDetailsBinding activityTvShowDetailsBinding;
    private TVShowDetailsViewModel tvShowDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvShowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show_details);

        doInitialization();
    }

    private void doInitialization() {
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
        getTVShowDetails();
    }

    private void getTVShowDetails(){
        activityTvShowDetailsBinding.setIsLoading(true);
        String tvShowId = String.valueOf(getIntent().getIntExtra("id",-1));
        tvShowDetailsViewModel.getTVShowDetails(tvShowId)
                .observe(this, tvShowDetailsResponse -> {
                    activityTvShowDetailsBinding.setIsLoading(false);
                    if (tvShowDetailsResponse.getTvShowDetails().getPictures() != null){
                        loadImageSlider(tvShowDetailsResponse.getTvShowDetails().getPictures());
                    }
        });
    }

    private void loadImageSlider(String[] sliderImages){
        activityTvShowDetailsBinding.sliderViewpager.setOffscreenPageLimit(1);
        activityTvShowDetailsBinding.sliderViewpager.setAdapter(new ImageSliderAdapter(sliderImages));
        activityTvShowDetailsBinding.sliderViewpager.setVisibility(View.VISIBLE);
        activityTvShowDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
    }
}