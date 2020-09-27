package com.elhady.tvshows.models;

import com.google.gson.annotations.SerializedName;

public class Episode {

	@SerializedName("air_date")
	private String airDate;

	@SerializedName("name")
	private String name;

	@SerializedName("season")
	private String season;

	@SerializedName("episode")
	private String episode;

	public String getAirDate(){
		return airDate;
	}

	public String getName(){
		return name;
	}

	public String getSeason(){
		return season;
	}

	public String getEpisode(){
		return episode;
	}
}