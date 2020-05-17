package com.app.marvel.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.marvel.R;
import com.app.marvel.modal.Character;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private static final String TAG = CharacterAdapter.class.getSimpleName();
    Context context;
    List<Object> characterList;

    public itemClickListener listener;


    public CharacterAdapter(Context context, List<Object> characterList) {
        this.context = context;
        this.characterList = characterList;
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
        RelativeLayout item = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.rl_character_list_item, null);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ///  Character character = (Character) characterList.get(position);
        String itemJson = new Gson().toJson(characterList.get(position));
        Character character = new Gson().fromJson(itemJson, Character.class);
        Log.d(TAG, "onBindViewHolder: " + new Gson().toJson(characterList.get(position)));
        holder.tvCharacterName.setText(character.getName());
        holder.tvIndex.setText(position + 1 + ".");
        Glide.with(context).load(character.getThumbnail().getFullPath()).placeholder(R.mipmap.character_loading).into(holder.imgCharacter);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(character));
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgCharacter;
        TextView tvCharacterName, tvIndex;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCharacter = itemView.findViewById(R.id.imgCharacter);
            tvCharacterName = itemView.findViewById(R.id.tvCharacterName);
            tvIndex = itemView.findViewById(R.id.tvIndex);
        }
    }
}
