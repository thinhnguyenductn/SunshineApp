package com.example.sunshineapp;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface dayDataDao {
    @Insert
    void insert(dayDataEntity dayDataEntity);

    @Update
    void update(dayDataEntity dayDataEntity);

    @Delete
    void delete(dayDataEntity dataEntity);

    @Query("DELETE FROM dayData")
    void deleteAllDayData();

    @Query("SELECT * FROM dayData")
    LiveData<List<dayDataEntity>> getAllDayDataEntity();
}
