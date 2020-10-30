package com.elhady.tvshows.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.elhady.tvshows.R;
import com.elhady.tvshows.databinding.ItemContainerTvShowBinding;
import com.elhady.tvshows.listener.WatchListListener;
import com.elhady.tvshows.models.TVShow;

import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.TVShowViewHolder> {
    private List<TVShow> tvShows;
    private LayoutInflater layoutInflater;
    private WatchListListener watchListListener;

    public WatchListAdapter(List<TVShow> tvShows, WatchListListener watchListListener) {
        this.tvShows = tvShows;
        this.watchListListener = watchListListener;
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerTvShowBinding tvShowBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.item_container_tv_show, parent, false);
        return new TVShowViewHolder(tvShowBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {
        holder.bindTVShow(tvShows.get(position));

    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    class TVShowViewHolder extends RecyclerView.ViewHolder {

        private ItemContainerTvShowBinding itemContainerTvShowBinding;

        public TVShowViewHolder(@NonNull ItemContainerTvShowBinding itemContainerTvShowBinding) {
            super(itemContainerTvShowBinding.getRoot());
            this.itemContainerTvShowBinding = itemContainerTvShowBinding;
        }

        public void bindTVShow(TVShow tvShow) {
            itemContainerTvShowBinding.setTvShow(tvShow);
            itemContainerTvShowBinding.executePendingBindings();
            itemContainerTvShowBinding.getRoot().setOnClickListener(view -> watchListListener.onTvShowClicked(tvShow));
            itemContainerTvShowBinding.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    watchListListener.removedTvShowFromWatchList(tvShow,getAdapterPosition());
                    itemContainerTvShowBinding.imageDelete.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
