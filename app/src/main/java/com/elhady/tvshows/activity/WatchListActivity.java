package com.elhady.tvshows.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.elhady.tvshows.R;
import com.elhady.tvshows.adapters.WatchListAdapter;
import com.elhady.tvshows.databinding.ActivityWatchListBinding;
import com.elhady.tvshows.listener.WatchListListener;
import com.elhady.tvshows.models.TVShow;
import com.elhady.tvshows.viewmodel.WatchlistViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity implements WatchListListener {

    private ActivityWatchListBinding binding;
    private WatchlistViewModel viewModel;
    private WatchListAdapter watchListAdapter;
    private List<TVShow> watchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_watch_list);
        doInitialization();
    }

    private void doInitialization() {
        viewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        binding.imageBack.setOnClickListener(view -> onBackPressed());
        watchList = new ArrayList<>();
    }

    private void loadWatchList() {
        binding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.loadWatchList().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShows -> {
                    binding.setIsLoading(false);
                    if (watchList.size() > 0) {
                        watchList.clear();
                    }
                    watchList.addAll(tvShows);
                    watchListAdapter = new WatchListAdapter(tvShows, this);
                    binding.watchListRecyclerview.setAdapter(watchListAdapter);
                    binding.watchListRecyclerview.setVisibility(View.VISIBLE);
                    compositeDisposable.dispose();
                }));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadWatchList();
    }


    @Override
    public void onTvShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
    }

    @Override
    public void removedTvShowFromWatchList(TVShow tvShow, int position) {

    }
}