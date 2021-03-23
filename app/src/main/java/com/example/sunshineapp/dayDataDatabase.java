package com.example.sunshineapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = dayDataEntity.class, version = 1)
public abstract class dayDataDatabase extends RoomDatabase {
    private static dayDataDatabase instance;
    public abstract dayDataDao dayDataDao();
    public static synchronized dayDataDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), dayDataDatabase.class, "day_Data")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
