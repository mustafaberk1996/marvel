package com.app.marvel.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.marvel.ApiClient;
import com.app.marvel.utility.Constants;
import com.app.marvel.R;
import com.app.marvel.utility.Utility;
import com.app.marvel.adapter.ComicAdapter;
import com.app.marvel.modal.BaseResponse;
import com.app.marvel.modal.Character;
import com.app.marvel.service.CharacterService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = CharacterDetailsActivity.class.getSimpleName();
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.rvComics)
    RecyclerView rvComics;
    @BindView(R.id.rlPb)
    RelativeLayout rlPb;

    ComicAdapter comicAdapter;
    Character character;
    List<Object> comicsList;
    CharacterService characterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);
        ButterKnife.bind(this);
        characterService = ApiClient.getClient().create(CharacterService.class);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        String characterJson = getIntent().getStringExtra(Constants.EXTRA_CHARACTER);
        Log.d(TAG, "onCreate: " + characterJson);
        character = new Gson().fromJson(characterJson, Character.class);
        actionBar.setTitle(character.getName());
        Glide.with(this).load(character.getThumbnail().getFullPath()).into(imgProfile);
        tvName.setText(character.getName());
        tvDescription.setText(character.getDescription().isEmpty() ? "-açıklama yok-" : character.getDescription());

        loadCommics(character.getId());
    }

    private void loadCommics(int characterId) {
        Date date = new Date();
        long ts = date.getTime();
        String hash = Utility.getHashForApi(ts);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateRange = "2005-01-01" + "," + dateFormat.format(date);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvComics.setLayoutManager(linearLayoutManager);
        characterService.loadComics(characterId, ts, Constants.PUBLIC_API_KEY, hash, dateRange, "modified", 10).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.d(TAG, "onResponse: " + response.toString());
                rlPb.setVisibility(View.GONE);
                if (response.body() != null && response.body().getData() != null && response.body().getData().getResults() != null) {
                    if (response.body().getData().getCount() > 0) {
                        comicsList = new ArrayList<>();
                        comicsList = response.body().getData().getResults();
                        comicAdapter = new ComicAdapter(CharacterDetailsActivity.this, comicsList);
                        rvComics.setAdapter(comicAdapter);
                    } else
                        Toast.makeText(CharacterDetailsActivity.this, R.string.comics_not_found, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(CharacterDetailsActivity.this, R.string.comics_not_found, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                rlPb.setVisibility(View.GONE);
                Toast.makeText(CharacterDetailsActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case android.R.id.home:
                finish();
                break;
        }
    }
}
