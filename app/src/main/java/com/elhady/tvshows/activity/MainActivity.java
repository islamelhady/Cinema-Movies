package com.elhady.tvshows.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.elhady.tvshows.R;
import com.elhady.tvshows.adapters.TVShowAdapter;
import com.elhady.tvshows.databinding.ActivityMainBinding;
import com.elhady.tvshows.listener.TVShowListener;
import com.elhady.tvshows.models.TVShow;
import com.elhady.tvshows.viewmodel.MostPopularTVShowViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowListener {

    private ActivityMainBinding activityMainBinding;
    private MostPopularTVShowViewModel viewModel;
    private List<TVShow> tvShow = new ArrayList<>();
    private TVShowAdapter adapter;
    private int currentPage = 1;
    private int totalAvailablePage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        doInitialization();

    }

    private void doInitialization() {
        activityMainBinding.tvShowRecyclerview.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowViewModel.class);
        adapter = new TVShowAdapter(tvShow, this);
        activityMainBinding.tvShowRecyclerview.setAdapter(adapter);
        activityMainBinding.tvShowRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMainBinding.tvShowRecyclerview.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePage) {
                        currentPage += 1;
                        mostPopularTVShow();
                    }
                }
            }
        });

        activityMainBinding.imageWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WatchListActivity.class));
            }
        });
        mostPopularTVShow();
    }

    private void mostPopularTVShow() {
        toggleLoading();
        viewModel.getMostPopularTVShow(currentPage).observe(this, mostPopularTVshowResponse -> {
            toggleLoading();
            if (mostPopularTVshowResponse != null) {
                totalAvailablePage = mostPopularTVshowResponse.getPages();
                if (mostPopularTVshowResponse.getTVShows() != null) {
                    int oldCount = tvShow.size();
                    tvShow.addAll(mostPopularTVshowResponse.getTVShows());
                    adapter.notifyItemRangeInserted(oldCount, tvShow.size());
                }
            }

        });
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()) {
                activityMainBinding.setIsLoading(false);
            } else {
                activityMainBinding.setIsLoading(true);
            }
        } else {
            if (activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()) {
                activityMainBinding.setIsLoadingMore(false);
            } else {
                activityMainBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
    }
}