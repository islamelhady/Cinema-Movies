package com.elhady.cinema_movies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.elhady.cinema_movies.models.TVShowResponse;
import com.elhady.cinema_movies.repository.MostPopularTVShowRepository;

public class MostPopularTVShowViewModel extends ViewModel {
    private MostPopularTVShowRepository mostPopularTVShowRepository;

    public MostPopularTVShowViewModel() {
        mostPopularTVShowRepository = new MostPopularTVShowRepository();
    }

    public LiveData<TVShowResponse> getMostPopularTVShow (int page){
        return mostPopularTVShowRepository.getMostPopularTVShows(page);
    }
}
