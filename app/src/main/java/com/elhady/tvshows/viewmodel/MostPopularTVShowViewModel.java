package com.elhady.tvshows.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.elhady.tvshows.models.TVShowResponse;
import com.elhady.tvshows.repository.MostPopularTVShowRepository;

public class MostPopularTVShowViewModel extends ViewModel {
    private MostPopularTVShowRepository mostPopularTVShowRepository;

    public MostPopularTVShowViewModel() {
        mostPopularTVShowRepository = new MostPopularTVShowRepository();
    }

    public LiveData<TVShowResponse> getMostPopularTVShow (int page){
        return mostPopularTVShowRepository.getMostPopularTVShows(page);
    }
}
