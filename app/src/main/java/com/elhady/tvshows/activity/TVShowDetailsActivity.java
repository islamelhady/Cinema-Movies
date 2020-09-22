package com.elhady.tvshows.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.elhady.tvshows.R;
import com.elhady.tvshows.adapters.ImageSliderAdapter;
import com.elhady.tvshows.databinding.ActivityTvShowDetailsBinding;
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

    private void getTVShowDetails() {
        activityTvShowDetailsBinding.setIsLoading(true);
        String tvShowId = String.valueOf(getIntent().getIntExtra("id", -1));
        tvShowDetailsViewModel.getTVShowDetails(tvShowId)
                .observe(this, tvShowDetailsResponse -> {
                    activityTvShowDetailsBinding.setIsLoading(false);
                    if (tvShowDetailsResponse.getTvShowDetails().getPictures() != null) {
                        loadImageSlider(tvShowDetailsResponse.getTvShowDetails().getPictures());
                    }
                });
    }

    private void loadImageSlider(String[] sliderImages) {
        activityTvShowDetailsBinding.sliderViewpager.setOffscreenPageLimit(1);
        activityTvShowDetailsBinding.sliderViewpager.setAdapter(new ImageSliderAdapter(sliderImages));
        activityTvShowDetailsBinding.sliderViewpager.setVisibility(View.VISIBLE);
        activityTvShowDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicators(sliderImages.length);
        activityTvShowDetailsBinding.sliderViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicators(position);
            }
        });
    }

    private void setupSliderIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext()
                    , R.drawable.background_slide_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            activityTvShowDetailsBinding.layoutSliderIndicators.addView(indicators[i]);
        }
        activityTvShowDetailsBinding.layoutSliderIndicators.setVisibility(View.VISIBLE);
        setCurrentSliderIndicators(0);
    }

    private void setCurrentSliderIndicators(int position) {
        int childCount = activityTvShowDetailsBinding.layoutSliderIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) activityTvShowDetailsBinding.layoutSliderIndicators.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slide_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slide_indicator_inactive));
            }
        }
    }
}