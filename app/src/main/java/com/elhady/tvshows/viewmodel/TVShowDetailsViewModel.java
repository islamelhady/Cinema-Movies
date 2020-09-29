package com.elhady.tvshows.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.elhady.tvshows.database.TvShowDatabase;
import com.elhady.tvshows.models.TVShow;
import com.elhady.tvshows.models.TVShowDetailsResponse;
import com.elhady.tvshows.repository.TVShowDetailsRepository;

import io.reactivex.Completable;

public class TVShowDetailsViewModel extends AndroidViewModel {
    private TVShowDetailsRepository tvShowDetailsRepository;
    private TvShowDatabase tvShowDatabase;

    public TVShowDetailsViewModel(@NonNull Application application) {
        super(application);
        this.tvShowDetailsRepository = new TVShowDetailsRepository();
        this.tvShowDatabase = TvShowDatabase.getTvShowDatabase(application);
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId){
        return tvShowDetailsRepository.getTVShowDetails(tvShowId);
    }

    public Completable addToWatchList(TVShow tvShow){
        return tvShowDatabase.tvShowDao().addToWatchList(tvShow);
    }
}
