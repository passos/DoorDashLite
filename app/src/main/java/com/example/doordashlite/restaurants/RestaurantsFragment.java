package com.example.doordashlite.restaurants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doordashlite.R;
import com.example.doordashlite.common.LoadingFragment;
import com.example.doordashlite.data.Address;
import com.example.doordashlite.data.Restaurant;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantsFragment extends LoadingFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.status)
    TextView statusView;

    private RestaurantsAdapter adapter;
    private RestaurantsViewModel viewModel;

    public RestaurantsFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.init(new Address("Test", 37.422740, -122.139956));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurants, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        adapter = new RestaurantsAdapter(viewModel);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(getActivity()).get(RestaurantsViewModel.class);
        viewModel.getItems().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                adapter.setData(restaurants);
            }
        });

        viewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                showLoading(aBoolean);
            }
        });

        viewModel.loadingError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String value) {
                showMsg(value);
            }
        });
    }
}
