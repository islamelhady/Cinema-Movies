package com.elhady.cinema_movies.network;

import com.elhady.cinema_movies.models.TVShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("most-popular")
    Call<TVShowResponse> getMostPopularTVShows(@Query("page") int page);
}
