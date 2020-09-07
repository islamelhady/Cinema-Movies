package com.elhady.tvshows.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.elhady.tvshows.R;
import com.elhady.tvshows.adapters.TVShowAdapter;
import com.elhady.tvshows.databinding.ActivityMainBinding;
import com.elhady.tvshows.models.TVShow;
import com.elhady.tvshows.viewmodel.MostPopularTVShowViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private MostPopularTVShowViewModel viewModel;
    private List<TVShow> tvShow = new ArrayList<>();
    private TVShowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        doInitialization();

    }

    private void doInitialization() {
        activityMainBinding.tvShowRecyclerview.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowViewModel.class);
        adapter = new TVShowAdapter(tvShow);
        activityMainBinding.tvShowRecyclerview.setAdapter(adapter);
        mostPopularTVShow();
    }

    private void mostPopularTVShow() {
        activityMainBinding.setIsLoading(true);
        viewModel.getMostPopularTVShow(0).observe(this, mostPopularTVshowResponse -> {
            activityMainBinding.setIsLoading(false);
            if (mostPopularTVshowResponse != null) {
                if (mostPopularTVshowResponse.getTVShows() != null) {
                    tvShow.addAll(mostPopularTVshowResponse.getTVShows());
                    adapter.notifyDataSetChanged();
                }
            }

        });
    }
}