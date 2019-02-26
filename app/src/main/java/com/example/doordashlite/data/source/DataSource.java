package com.example.doordashlite.data.source;

import androidx.annotation.NonNull;

public interface DataSource<P, T> {
    void getList(P param, @NonNull LoadDataCallback<T> callback);

    void getItem(@NonNull String id, @NonNull LoadDataCallback<T> callback);

    void refreshList();
}
