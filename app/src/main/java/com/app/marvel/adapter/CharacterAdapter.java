package com.app.marvel.adapter;

import android.content.Context;
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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    Context context;
    List<Character> characterList;

    public itemClickListener listener;


    public CharacterAdapter(Context context, List<Character> characterList) {
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
        Character character = characterList.get(position);
        holder.tvCharacterName.setText(character.getName());
        Glide.with(context).load(character.getPhoto_url()).into(holder.imgCharacter);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(character));
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgCharacter;
        TextView tvCharacterName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCharacter = itemView.findViewById(R.id.imgCharacter);
            tvCharacterName = itemView.findViewById(R.id.tvCharacterName);
        }
    }
}
