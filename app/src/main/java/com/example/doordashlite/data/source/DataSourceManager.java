package com.example.doordashlite.data.source;

import java.util.LinkedHashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class DataSourceManager {
    private volatile static DataSourceManager INSTANCE;
    private Map<Class<? extends DataSource>, DataSource> dataSources;

    private DataSourceManager() {
        dataSources = new LinkedHashMap<>();
    }

    public static DataSourceManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DataSourceManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataSourceManager();
                }
            }
        }
        return INSTANCE;
    }

    public void register(Class<? extends DataSource> clazz, DataSource dataSource) {
        dataSources.put(clazz, dataSource);
    }

    @NonNull
    public <T extends DataSource> T get(Class<T> clazz) {
        DataSource dataSource = dataSources.get(clazz);

        if (dataSource == null) {
            try {
                dataSource = clazz.newInstance();
                dataSources.put(clazz, dataSource);
            } catch (Exception e) {
                return null;
            }
        }

        //noinspection unchecked
        return (T) dataSource;
    }
}
