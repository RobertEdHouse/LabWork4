package com.example.labwork4.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.labwork4.model.Holiday;
import com.example.labwork4.model.Model;

import java.util.Collections;
import java.util.List;

public class HolidayViewModel extends ViewModel {
    private Model model;
    private MutableLiveData<List<Holiday>> holidaysData =
            new MutableLiveData<>();
    {
        holidaysData.setValue(Collections.emptyList());
    }
    public LiveData<List<Holiday>> getHolidays() {
        update(true);
        return holidaysData;
    }
    public void loadHolidays() {
        model.update();
        holidaysData.setValue(model.getListHoliday());
    }
    public void createModel(Context context){
        model=new Model(this,context);
        model.setContext(context);
    }

    public void update(boolean isUpdateModel){
        if(!isUpdateModel)
            holidaysData.setValue(model.getListHoliday());
        else loadHolidays();
    }




}
