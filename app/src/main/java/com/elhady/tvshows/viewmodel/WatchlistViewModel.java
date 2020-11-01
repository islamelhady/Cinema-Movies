package com.elhady.tvshows.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.elhady.tvshows.database.TvShowDatabase;
import com.elhady.tvshows.models.TVShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchlistViewModel extends AndroidViewModel {

    private TvShowDatabase tvShowDatabase;

    public WatchlistViewModel(@NonNull Application application) {
        super(application);
        this.tvShowDatabase = TvShowDatabase.getTvShowDatabase(application);
    }

    public Flowable<List<TVShow>> loadWatchList(){
        return tvShowDatabase.tvShowDao().getWatchList();
    }

    public Completable removeTvShowFromWatchList(TVShow tvShow){
        return tvShowDatabase.tvShowDao().deleteFromWatchList(tvShow);
    }
}
