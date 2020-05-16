package com.app.marvel.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.app.marvel.ApiClient;
import com.app.marvel.Constants;
import com.app.marvel.EndlessRecyclerViewScrollListener;
import com.app.marvel.R;
import com.app.marvel.adapter.CharacterAdapter;
import com.app.marvel.modal.Character;
import com.app.marvel.service.CharacterService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EndlessRecyclerViewScrollListener scrollListener;

    CharacterService characterService;
    List<Character> characterList;
    CharacterAdapter characterAdapter;


    @BindView(R.id.rvCharacters)
    RecyclerView rvCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        characterService = ApiClient.getClient().create(CharacterService.class);

        initialLoad();

    }

    private void initialLoad() {

        //rlPb.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvCharacters.setLayoutManager(linearLayoutManager);
        loadData(0);
    }

    private void loadData(int index) {

        characterService.loadUsers(index).enqueue(new Callback<List<Character>>() {
            @Override
            public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {
                //rlPb.setVisibility(View.GONE);
                if (characterList != null) {
                    int startIndex = characterList.size();
                    characterList.addAll(response.body());
                    characterAdapter.notifyItemRangeInserted(startIndex, response.body().size());
                } else {
                    characterList = new ArrayList<>();
                    characterList = response.body();
                    characterAdapter = new CharacterAdapter(MainActivity.this, characterList);
                    characterAdapter.setOnItemClickListener(character -> {
                        Intent intent = new Intent(MainActivity.this, CharacterDetailsActivity.class);
                        intent.putExtra(Constants.EXTRA_CHARACTER, (Serializable) character);
                        intent.putExtra(Constants.EXTRA_CHARACTER_ID, character.getId());
                        intent.putExtra(Constants.EXTRA_CHARACTER_NAME, character.getName());
                        startActivity(intent);
                    });
                    rvCharacters.setAdapter(characterAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {
                //rlPb.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
