package com.elhady.tvshows.network;

import com.elhady.tvshows.models.TVShowDetailsResponse;
import com.elhady.tvshows.models.TVShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("most-popular")
    Call<TVShowResponse> getMostPopularTVShows(@Query("page") int page);
    
    @GET("show-details")
    Call<TVShowDetailsResponse> getTVShowsDetails(@Query("q") String tvShowId);

    @GET("search")
    Call<TVShowResponse> searchTVShows(@Query("q") String query, @Query("page") int page);
}
