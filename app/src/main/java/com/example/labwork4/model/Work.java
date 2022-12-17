package com.example.labwork4.model;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Work {

    Context context;
    List<Holiday> list;
    public Work(Context context) {
        this.context = context;
    }

    public void saveInDB(List<Holiday> holidays){
        if(compare(holidays))
            return;
        HolidaysDatabase db = HolidaysDatabase.getInstance(context);

        HolidayDao dao = db.getHolidayDao();
        dao.updateHoliday(holidays);
    }

    public List<Holiday> loadFromDB(){
        HolidaysDatabase db = HolidaysDatabase.getInstance(context);
        HolidayDao dao = db.getHolidayDao();
        list = dao.getHolidays();

        return list;
    }

    public boolean compare(List<Holiday> newHolidays){
        List<Holiday> holidaysDB = loadFromDB();
        if(newHolidays.size()==0||holidaysDB.size()==0)
            return false;
        for(Holiday nh:newHolidays){
            for (Holiday h:holidaysDB) {
                if(nh.name.compareTo(h.name)==0)
                    if(!nh.isEqual(h))
                        return false;
            }
        }
        return true;
    }

}
