package com.app.marvel.service;

import com.app.marvel.modal.Character;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CharacterService  {


    @GET("users/leader-board/{index}")
    Call<List<Character>> loadUsers(@Path("index") int index);


}
