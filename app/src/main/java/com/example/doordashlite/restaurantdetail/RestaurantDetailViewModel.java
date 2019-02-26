package com.example.doordashlite.restaurantdetail;

import android.app.Application;

import com.example.doordashlite.data.Restaurant;
import com.example.doordashlite.data.source.DataSourceManager;
import com.example.doordashlite.data.source.RestaurantsRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class RestaurantDetailViewModel extends AndroidViewModel {
    private final MutableLiveData<Restaurant> restaurant = new MutableLiveData<>();
    private final RestaurantsRepository repository;

    public RestaurantDetailViewModel(@NonNull Application application) {
        super(application);
        this.repository = DataSourceManager.getInstance().get(RestaurantsRepository.class);
    }
}
