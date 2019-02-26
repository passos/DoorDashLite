package com.example.doordashlite.data.source;

import com.example.doordashlite.data.Address;
import com.example.doordashlite.data.Restaurant;
import com.example.doordashlite.data.source.remote.RestaurantsRemoteDataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RestaurantsRepositoryTest {

    private RestaurantsRepository repository;

    @Mock
    private RestaurantsRemoteDataSource remoteDataSource;

    @Mock
    private LoadDataCallback<Restaurant> loadDataCallback;

    @Mock
    private Address address;

    @Captor
    private ArgumentCaptor<LoadDataCallback> loadDataCallbackArgumentCaptor;

    private ArrayList restaurants = new ArrayList<Restaurant>() {
        {
            add(new Restaurant());
            add(new Restaurant());
            add(new Restaurant());
        }
    };

    @Before
    public void setupRestaurantsRepository() {
        MockitoAnnotations.initMocks(this);

        repository = new RestaurantsRepository();
        repository.setRestaurantsRemoteDataSource(remoteDataSource);
    }

    @Test
    public void getRestaurants_getListFromRemoteDataSource() {
        repository.getList(address, loadDataCallback);
        verify(remoteDataSource).getList(eq(address), loadDataCallbackArgumentCaptor.capture());
        loadDataCallbackArgumentCaptor.getValue().onListLoaded(restaurants);

        // should load from cache
        repository.getList(address, loadDataCallback);
        verify(remoteDataSource, times(1)).getList(any(Address.class), any(LoadDataCallback.class));
    }

    @Test
    public void getRestaurants_requestsAllRestaurantFromRemoteDataSource() {
        repository.getItem("000", loadDataCallback);
        verify(remoteDataSource).getItem(eq("000"), any(LoadDataCallback.class));
    }

    @Test
    public void getRestaurants_refreshList() {
        repository.refreshList();
        repository.getList(address, loadDataCallback);
        verify(remoteDataSource).getList(eq(address), any(LoadDataCallback.class));
    }
}
