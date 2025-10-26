package com.example.comp3074uiwireframe;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executors;

public class InventoryViewModel extends AndroidViewModel {

    private InventoryDao dao;
    private InventoryDatabase database;
    private LiveData<List<InventoryItem>> allItems;

    public InventoryViewModel(@NonNull Application application) {
        super(application);
        database = InventoryDatabase.getInstance(application);
        dao = database.inventoryDao();
        allItems = dao.getAllItems();
    }

    public LiveData<List<InventoryItem>> getAllItems() {
        return allItems;
    }

    public void insert(InventoryItem item) {
        Executors.newSingleThreadExecutor().execute(() -> dao.insert(item));
    }

    public void update(InventoryItem item) {
        Executors.newSingleThreadExecutor().execute(() -> dao.update(item));
    }

    public void delete(InventoryItem item) {
        Executors.newSingleThreadExecutor().execute(() -> dao.delete(item));
    }
}
