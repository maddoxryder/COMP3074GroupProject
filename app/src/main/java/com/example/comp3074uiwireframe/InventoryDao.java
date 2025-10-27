package com.example.comp3074uiwireframe;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface InventoryDao {
    @Insert
    void insert(InventoryItem item);

    @Update
    void update(InventoryItem item);

    @Delete
    void delete(InventoryItem item);

    @Query("DELETE FROM inventory_items")
    void deleteAll();

    @Query("SELECT * FROM inventory_items ORDER BY name ASC")
    LiveData<List<InventoryItem>> getAllItems();
}
