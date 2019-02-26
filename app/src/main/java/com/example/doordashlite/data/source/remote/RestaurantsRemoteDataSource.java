package com.example.doordashlite.data.source.remote;

import com.example.doordashlite.DoorDashLiteApplication;
import com.example.doordashlite.data.Address;
import com.example.doordashlite.data.Restaurant;
import com.example.doordashlite.data.source.DataSource;
import com.example.doordashlite.data.source.LoadDataCallback;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantsRemoteDataSource implements DataSource<Address, Restaurant> {
    private DoorDashService service;

    public RestaurantsRemoteDataSource() {
        service = DoorDashLiteApplication.getRetrofit().create(DoorDashService.class);
    }

    @Override
    public void getList(@NonNull Address address, @NonNull final LoadDataCallback<Restaurant> callback) {
        Call<List<Restaurant>> call = service.getRestaurants(address.lat, address.lng, 0, 50);
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(@NonNull Call<List<Restaurant>> call, @NonNull Response<List<Restaurant>> response) {
                callback.onListLoaded(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Restaurant>> call, @NonNull Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void getItem(@NonNull String id, @NonNull final LoadDataCallback<Restaurant> callback) {
        final Call<Restaurant> call = service.getRestaurant(id);
        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                callback.onDataLoaded(response.body());
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void refreshList() {
    }
}
