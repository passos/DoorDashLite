package com.example.doordashlite.restaurants;

import android.app.Application;

import com.example.doordashlite.data.Address;
import com.example.doordashlite.data.Restaurant;
import com.example.doordashlite.data.source.DataSourceManager;
import com.example.doordashlite.data.source.LoadDataCallback;
import com.example.doordashlite.data.source.RestaurantsRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RestaurantsViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Restaurant>> items = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> loadingError = new MutableLiveData<>();

    private RestaurantsRepository repository;
    private Address address;

    public RestaurantsViewModel(@NonNull Application application) {
        super(application);
        this.repository = DataSourceManager.getInstance().get(RestaurantsRepository.class);
    }

    public void setRestaurantsRepository(@NonNull RestaurantsRepository repository) {
        this.repository = repository;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public LiveData<String> loadingError() {
        return loadingError;
    }

    public LiveData<List<Restaurant>> getItems() {
        return items;
    }

    public void init(Address address) {
        if (address == null) {
            return;
        }
        if (!address.equals(this.address)) {
            this.address = address;
            loadData(true, true);
        } else {
            loadData(false, true);
        }
    }

    void loadData(boolean forceUpdate, final boolean showLoadingUI) {
        if (forceUpdate) {
            repository.refreshList();
        }

        if (showLoadingUI) {
            isLoading.setValue(true);
        }

        repository.getList(this.address, new LoadDataCallback<Restaurant>() {
            @Override
            public void onListLoaded(List<Restaurant> list) {
                if (showLoadingUI) {
                    isLoading.setValue(false);
                }
                loadingError.setValue("");

                List<Restaurant> itemsValue = new ArrayList<>(list);
                items.setValue(itemsValue);
            }

            @Override
            public void onDataLoaded(Restaurant data) {
            }

            @Override
            public void onDataNotAvailable(String error) {
                if (showLoadingUI) {
                    isLoading.setValue(false);
                }
                loadingError.setValue(error);
            }
        });
    }
}
