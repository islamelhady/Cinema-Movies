package com.elhady.tvshows.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TVShowDetails {

	@SerializedName("url")
	private String url;

	@SerializedName("description")
	private String description;

	@SerializedName("runtime")
	private int runtime;

	@SerializedName("image_path")
	private String imagePath;

	@SerializedName("rating")
	private String rating;

	@SerializedName("genres")
	private List<String> genres;

	@SerializedName("pictures")
	private List<String> pictures;

	@SerializedName("episodes")
	private List<Episode> episodes;

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}

	public int getRuntime() {
		return runtime;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getRating() {
		return rating;
	}

	public List<String> getGenres() {
		return genres;
	}

	public List<String> getPictures() {
		return pictures;
	}

	public List<Episode> getEpisodes() {
		return episodes;
	}
}