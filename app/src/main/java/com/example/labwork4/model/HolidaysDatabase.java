package com.example.labwork4.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Holiday.class}, version = 1,exportSchema = false)
public abstract class HolidaysDatabase extends RoomDatabase{
    public abstract HolidayDao getHolidayDao();
    private static HolidaysDatabase instance;
    public static synchronized HolidaysDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),HolidaysDatabase.class,"HolidayBD.db").fallbackToDestructiveMigration().build();

        }
        return instance;

    }
}
