package com.elhady.tvshows.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewPropertyAnimator;

import com.elhady.tvshows.R;
import com.elhady.tvshows.adapters.TVShowAdapter;
import com.elhady.tvshows.databinding.ActivityMainBinding;
import com.elhady.tvshows.databinding.ActivitySearchBinding;
import com.elhady.tvshows.listener.TVShowListener;
import com.elhady.tvshows.models.TVShow;
import com.elhady.tvshows.viewmodel.MostPopularTVShowViewModel;
import com.elhady.tvshows.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements TVShowListener {

    private ActivitySearchBinding activitySearchBinding;
    private SearchViewModel viewModel;
    private List<TVShow> tvShow = new ArrayList<>();
    private TVShowAdapter adapter;
    private int currentPage = 1;
    private int totalAvailablePage = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        doInitialization();
    }

    private void doInitialization(){
        activitySearchBinding.imageBack.setOnClickListener(v -> onBackPressed());
        activitySearchBinding.tvShowRecyclerview.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        adapter = new TVShowAdapter(tvShow, this);
        activitySearchBinding.tvShowRecyclerview.setAdapter(adapter);
        activitySearchBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null){
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().isEmpty()){
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    currentPage = 1;
                                    totalAvailablePage = 1;
                                    searchTVShow(editable.toString());
                                }
                            });

                        }
                    },500);
                }else {
                    tvShow.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        activitySearchBinding.tvShowRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activitySearchBinding.tvShowRecyclerview.canScrollVertically(1)){
                    if (!activitySearchBinding.inputSearch.getText().toString().isEmpty()){
                        if (currentPage < totalAvailablePage){
                            currentPage += 1;
                            searchTVShow(activitySearchBinding.inputSearch.getText().toString());
                        }
                    }
                }
            }
        });
        activitySearchBinding.inputSearch.requestFocus();
    }

    private void searchTVShow(String query){
        toggleLoading();
        viewModel.searchTVShow(query, currentPage).observe(this, tvShowResponse -> {
            toggleLoading();
            if (tvShowResponse != null) {
                totalAvailablePage = tvShowResponse.getPages();
                if (tvShowResponse.getTVShows() != null) {
                    int oldCount = tvShow.size();
                    tvShow.addAll(tvShowResponse.getTVShows());
                    adapter.notifyItemRangeInserted(oldCount, tvShow.size());
                }
            }
        });

    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (activitySearchBinding.getIsLoading() != null && activitySearchBinding.getIsLoading()) {
                activitySearchBinding.setIsLoading(false);
            } else {
                activitySearchBinding.setIsLoading(true);
            }
        } else {
            if (activitySearchBinding.getIsLoadingMore() != null && activitySearchBinding.getIsLoadingMore()) {
                activitySearchBinding.setIsLoadingMore(false);
            } else {
                activitySearchBinding.setIsLoadingMore(true);
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