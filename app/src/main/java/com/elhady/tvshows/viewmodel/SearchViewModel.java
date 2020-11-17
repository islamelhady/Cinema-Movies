package com.elhady.tvshows.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.elhady.tvshows.models.TVShowResponse;
import com.elhady.tvshows.repository.SearchTVShowRepository;

public class SearchViewModel extends ViewModel {
    private SearchTVShowRepository searchTVShowRepository;

    public SearchViewModel() {
        searchTVShowRepository = new SearchTVShowRepository();
    }

    public LiveData<TVShowResponse> searchTVShow(String query, int page){
        return searchTVShowRepository.searchTVShow(query, page);
    }
}




