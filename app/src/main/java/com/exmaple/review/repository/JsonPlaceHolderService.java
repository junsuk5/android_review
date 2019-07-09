package com.exmaple.review.repository;

import com.exmaple.review.models.Album;
import com.exmaple.review.models.Photo;
import com.exmaple.review.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderService {
    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("users")
    Call<List<User>> getUsers();

    @GET("albums")
    Call<List<Album>> getAlbums(@Query("userId") int userId);

    @GET("photos")
    Call<List<Photo>> getPhotos(@Query("albumId") int albumId);
}
