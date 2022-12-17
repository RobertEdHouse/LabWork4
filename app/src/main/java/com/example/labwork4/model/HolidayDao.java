package com.example.labwork4.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public abstract class HolidayDao {
    @Query("SELECT * FROM holidays")
    public abstract List<Holiday> getHolidays();
    @Insert
    public abstract void insert(Holiday holiday);
    @Insert
    public abstract long[] insert(List<Holiday> holidays);
    @Query("DELETE FROM holidays")
    public abstract void deleteAll();
    @Transaction
    public void updateHoliday(List<Holiday> newHolidays) {
        deleteAll();
        insert(newHolidays);
    }
    @Query("SELECT * FROM holidays WHERE name = :name")
    public abstract Holiday getHoliday(String name);

}
