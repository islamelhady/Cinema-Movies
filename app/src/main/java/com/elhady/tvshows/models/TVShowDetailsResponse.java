package com.elhady.tvshows.models;

import com.google.gson.annotations.SerializedName;

public class TVShowDetailsResponse {

	@SerializedName("tvShow")
	private TVShowDetails tvShowDetails;

	public TVShowDetails getTvShowDetails() {
		return tvShowDetails;
	}
}