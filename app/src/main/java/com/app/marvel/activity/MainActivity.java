package com.app.marvel.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.health.TimerStat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.marvel.ApiClient;
import com.app.marvel.Constants;
import com.app.marvel.EndlessRecyclerViewScrollListener;
import com.app.marvel.MD5;
import com.app.marvel.R;
import com.app.marvel.Utility;
import com.app.marvel.adapter.CharacterAdapter;
import com.app.marvel.modal.BaseResponse;
import com.app.marvel.modal.Character;
import com.app.marvel.service.CharacterService;
import com.google.gson.Gson;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.app.marvel.modal.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    EndlessRecyclerViewScrollListener scrollListener;

    CharacterService characterService;
    List<Object> characterList;
    CharacterAdapter characterAdapter;


    @BindView(R.id.rvCharacters)
    RecyclerView rvCharacters;

    @BindView(R.id.rlPb)
    RelativeLayout rlPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        characterService = ApiClient.getClient().create(CharacterService.class);
        initialLoad();
    }

    private void initialLoad() {

        rlPb.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvCharacters.setLayoutManager(linearLayoutManager);
        loadData(0);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadData(page);
                page++;
            }

            @Override
            public void onScrolled(RecyclerView view, int dx, int dy) {
                super.onScrolled(view, dx, dy);
            }
        };

        rvCharacters.addOnScrollListener(scrollListener);
    }

    private void loadData(int index) {
        int offset = Constants.C_L_LOAD_ITEM_SIZE * index + 1;
        Date date = new Date();
        long ts = date.getTime();
        String hash = Utility.getHashForApi(ts);
        characterService.loadUsers(ts, Constants.PUBLIC_API_KEY, hash, Constants.C_L_LOAD_ITEM_SIZE, offset).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                rlPb.setVisibility(View.GONE);
                if (response.body() != null && response.body().getData() != null && response.body().getData().getResults() != null) {

                    if (characterList != null) {
                        int startIndex = characterList.size();
                        characterList.addAll(response.body().getData().getResults());
                        characterAdapter.notifyItemRangeInserted(startIndex, response.body().getData().getResults().size());
                    } else {
                        characterList = new ArrayList<>();
                        characterList = response.body().getData().getResults();
                        characterAdapter = new CharacterAdapter(MainActivity.this, characterList);
                        characterAdapter.setOnItemClickListener(character -> {
                            Intent intent = new Intent(MainActivity.this, CharacterDetailsActivity.class);
                            intent.putExtra(Constants.EXTRA_CHARACTER, new Gson().toJson(character));
                            startActivity(intent);
                        });
                        rvCharacters.setAdapter(characterAdapter);
                    }
                } else
                    Toast.makeText(MainActivity.this, R.string.character_not_found, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                rlPb.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
