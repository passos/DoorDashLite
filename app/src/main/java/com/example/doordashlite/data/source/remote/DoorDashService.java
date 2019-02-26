package com.example.doordashlite.data.source.remote;

import com.example.doordashlite.data.Restaurant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DoorDashService {
    @GET("/v2/restaurant/")
    Call<List<Restaurant>> getRestaurants(@Query("lat") double lat, @Query("lng") double lng, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/v2/restaurant/{restaurant_id}")
    Call<Restaurant> getRestaurant(@Path("restaurant_id") String id);
}
