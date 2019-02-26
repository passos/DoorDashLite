package com.example.doordashlite.data.source;

import com.example.doordashlite.data.Address;
import com.example.doordashlite.data.Restaurant;
import com.example.doordashlite.data.source.remote.RestaurantsRemoteDataSource;
import com.example.doordashlite.util.Utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public class RestaurantsRepository implements DataSource<Address, Restaurant> {
    private DataSource<Address, Restaurant> restaurantsRemoteDataSource;
    private Map<String, Restaurant> cache;
    private boolean isCacheDirty = false;

    public RestaurantsRepository() {
        this.restaurantsRemoteDataSource = DataSourceManager.getInstance().get(RestaurantsRemoteDataSource.class);
    }

    public void setRestaurantsRemoteDataSource(DataSource<Address, Restaurant> dataSource) {
        this.restaurantsRemoteDataSource = dataSource;
    }

    @Override
    public void getList(@NonNull Address address, @NonNull final LoadDataCallback<Restaurant> callback) {
        Utils.checkNotNull(callback);

        if (cache != null && !isCacheDirty) {
            callback.onListLoaded(new ArrayList<>(cache.values()));
            return;
        }

        restaurantsRemoteDataSource.getList(address, new LoadDataCallback<Restaurant>() {
            @Override
            public void onListLoaded(List<Restaurant> list) {
                refreshCache(list);
                callback.onListLoaded(new ArrayList<>(cache.values()));
            }

            @Override
            public void onDataLoaded(Restaurant data) {
            }

            @Override
            public void onDataNotAvailable(String error) {
                callback.onDataNotAvailable(error);
            }
        });
    }

    @Override
    public void getItem(@NonNull String id, @NonNull final LoadDataCallback<Restaurant> callback) {
        Utils.checkNotNull(id);
        Utils.checkNotNull(callback);

        Restaurant cachedData = getRestaurantWithId(id);
        if (cachedData != null) {
            callback.onDataLoaded(cachedData);
            return;
        }

        restaurantsRemoteDataSource.getItem(id, new LoadDataCallback<Restaurant>() {
            @Override
            public void onListLoaded(List<Restaurant> list) {
            }

            @Override
            public void onDataLoaded(Restaurant data) {
                if (data == null) {
                    onDataNotAvailable("empty data");
                    return;
                }

                if (cache == null) {
                    cache = new LinkedHashMap<>();
                }
                cache.put(data.id, data);
                callback.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable(String error) {
                callback.onDataNotAvailable(error);
            }
        });
    }

    private Restaurant getRestaurantWithId(@NonNull String id) {
        Utils.checkNotNull(id);
        if (cache == null || cache.isEmpty()) {
            return null;
        } else {
            return cache.get(id);
        }
    }

    @Override
    public void refreshList() {
        isCacheDirty = true;
    }

    private void refreshCache(List<Restaurant> restaurants) {
        if (cache == null) {
            cache = new LinkedHashMap<>();
        }
        cache.clear();
        for (Restaurant restaurant : restaurants) {
            cache.put(restaurant.id, restaurant);
        }
        isCacheDirty = false;
    }
}
