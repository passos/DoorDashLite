package com.example.doordashlite.data.source;

import java.util.List;

public interface LoadDataCallback<T> {
    void onListLoaded(List<T> list);

    void onDataLoaded(T data);

    void onDataNotAvailable(String error);
}
