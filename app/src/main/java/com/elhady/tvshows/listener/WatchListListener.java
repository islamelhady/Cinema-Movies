package com.elhady.tvshows.listener;

import com.elhady.tvshows.models.TVShow;

public interface WatchListListener {
    void onTvShowClicked(TVShow tvShow);
    void removedTvShowFromWatchList(TVShow tvShow, int position);
}
