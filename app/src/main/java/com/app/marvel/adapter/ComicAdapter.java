package com.app.marvel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.marvel.R;
import com.app.marvel.modal.Character;
import com.app.marvel.modal.Comics;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {

    private static final String TAG = ComicAdapter.class.getSimpleName();
    Context context;
    List<Object> comicList;

    public itemClickListener listener;


    public ComicAdapter(Context context, List<Object> comicList) {
        this.context = context;
        this.comicList = comicList;
    }

    public void setOnItemClickListener(itemClickListener listener) {
        this.listener = listener;
    }

    public interface itemClickListener {
        void onItemClick(Character character);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RelativeLayout item = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.rl_comics_list_item, null);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String itemJson = new Gson().toJson(comicList.get(position));
        Comics comics = new Gson().fromJson(itemJson, Comics.class);
        holder.tvIndex.setText(position + 1 +". ");
        holder.tvMovieName.setText(comics.getTitle());
        Glide.with(context).load(comics.getThumbnail().getFullPath()).placeholder(R.mipmap.poster_loading).into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMovieName,tvIndex;
        ImageView imgPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMovieName = itemView.findViewById(R.id.tvMovieName);
            tvIndex = itemView.findViewById(R.id.tvIndex);
            imgPoster = itemView.findViewById(R.id.imgPoster);
        }
    }
}
