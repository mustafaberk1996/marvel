package com.app.marvel.service;

import com.app.marvel.Constants;
import com.app.marvel.modal.BaseResponse;
import com.app.marvel.modal.Character;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CharacterService {

    @GET("/v1/public/characters")
    Call<BaseResponse> loadUsers(@Query("ts") long timeStamp, @Query("apikey") String apikey, @Query("hash") String hash,
                                 @Query("limit") int limit,@Query("offset") int offset);


    @GET("/v1/public/characters/{id}/comics")
    Call<BaseResponse> loadComics(@Path("id") int characterId,@Query("ts") long timeStamp, @Query("apikey") String apikey, @Query("hash") String hash,
                                  @Query("dateRange") String dateRange,@Query("orderBy") String orderBy,@Query("limit") int limit);

    @GET("/v1/public/characters/{id}/comics")
    Call<BaseResponse> loadComics(@Path("id") int characterId,@Query("ts") long timeStamp, @Query("apikey") String apikey, @Query("hash") String hash);

//    https://gateway.marvel.com:443/v1/public/characters/1009189/comics?dateRange=2005-01-01%2C2020-01-02&orderBy=modified&limit=10&apikey=51c9756b5e794dd744171a169b3212c4
//
//    http://gateway.marvel.com/v1/public/characters/1009189/comics?
        // ts=1589728442040&apikey=51c9756b5e794dd744171a169b3212c4&hash=eae61e016e874dfc4f0fd8626f5ac02f&  limit=10&orderBy=modified&dateRange=2005-01-01%2C2020-14-17
}
