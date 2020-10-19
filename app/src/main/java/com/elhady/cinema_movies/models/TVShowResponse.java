package com.elhady.cinema_movies.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TVShowResponse {

	@SerializedName("tv_shows")
	private List<TvShow> tvShows;

	@SerializedName("total")
	private String total;

	@SerializedName("pages")
	private int pages;

	@SerializedName("page")
	private int page;

	public List<TvShow> getTvShows(){
		return tvShows;
	}

	public String getTotal(){
		return total;
	}

	public int getPages(){
		return pages;
	}

	public int getPage(){
		return page;
	}
}