package com.example.labwork4.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "holidays", indices = {@Index("name")})
public class Holiday implements Serializable {
    @PrimaryKey
    @NonNull
    @Json(name = "name")
    @ColumnInfo(name = "name")
    String name;

    @Json(name = "date")
    @ColumnInfo(name = "date")
    String date;

    @Json(name = "localName")
    @ColumnInfo(name = "localName")
    String localName;

    @Json(name = "launchYear")
    @ColumnInfo(name = "launchYear")
    String launchYear;

    public Holiday(@NonNull String name, String date, String localName, String  launchYear) {

        this.name = name;
        this.date = date;
        this.localName = localName;
        this.launchYear = launchYear;
    }

    public Holiday() {    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getLaunchYear() {
        return launchYear;
    }

    public void setLaunchYear(String launchYear) {
        this.launchYear = launchYear;
    }
    public boolean isEqual(Holiday holiday){
        if(holiday.name.compareTo(name)==0
                &&holiday.date.compareTo(date)==0
                &&holiday.localName.compareTo(localName)==0
                &&holiday.launchYear!=launchYear)
            return true;
        return false;
    }
}
