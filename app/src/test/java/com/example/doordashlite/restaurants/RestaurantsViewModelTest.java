package com.example.doordashlite.restaurants;

import android.app.Application;
import android.content.res.Resources;

import com.example.doordashlite.data.Address;
import com.example.doordashlite.data.Restaurant;
import com.example.doordashlite.data.source.LoadDataCallback;
import com.example.doordashlite.data.source.RestaurantsRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RestaurantsViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private RestaurantsRepository repository;

    @Mock
    private Application context;

    @Captor
    private ArgumentCaptor<LoadDataCallback> loadDataCallbackArgumentCaptor;

    private RestaurantsViewModel viewModel;

    @Mock
    private Address address;

    private ArrayList restaurants = new ArrayList<Restaurant>() {
        {
            add(new Restaurant());
            add(new Restaurant());
            add(new Restaurant());
        }
    };

    @Before
    public void setupRestaurantsViewModel() {
        MockitoAnnotations.initMocks(this);

        when(context.getApplicationContext()).thenReturn(context);
        when(context.getResources()).thenReturn(mock(Resources.class));

        viewModel = new RestaurantsViewModel(context);
        viewModel.setRestaurantsRepository(repository);
    }

    @Test
    public void loadData_init() {
        viewModel.init(address);
        verify(repository).getList(any(Address.class), loadDataCallbackArgumentCaptor.capture());
    }

    @Test
    public void loadData_loaded() {
        viewModel.init(address);
        verify(repository).getList(any(Address.class), loadDataCallbackArgumentCaptor.capture());

        assertTrue(viewModel.isLoading().getValue());
        loadDataCallbackArgumentCaptor.getValue().onListLoaded(restaurants);
        assertFalse(viewModel.isLoading().getValue());

        assertFalse(viewModel.getItems().getValue().isEmpty());
        assertTrue(viewModel.getItems().getValue().size() == 3);
    }

    @Test
    public void loadData_loadError() {
        viewModel.init(address);
        verify(repository).getList(any(Address.class), loadDataCallbackArgumentCaptor.capture());

        assertNull(viewModel.loadingError().getValue());
        loadDataCallbackArgumentCaptor.getValue().onDataNotAvailable("error");
        assertFalse(viewModel.loadingError().getValue().isEmpty());

        assertNull(viewModel.getItems().getValue());
    }
}
