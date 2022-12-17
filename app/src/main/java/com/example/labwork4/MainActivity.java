package com.example.labwork4;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.labwork4.model.Holiday;
import com.example.labwork4.view.HolidayAdapter;
import com.example.labwork4.viewmodel.HolidayViewModel;
import java.util.List;

import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity {
    private final String HOLIDAY="holiday";
    private ListView listView;
    HolidayViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.list_holidays);
        //listView.setAd
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel = ViewModelProviders.of(this)
                .get(HolidayViewModel.class);
        viewModel.createModel(this);
        viewModel.getHolidays()
                .observe(this, this::onHolidaysChanged);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getHolidays();
    }

    private void onHolidaysChanged(List<Holiday> holidays) {
        if (holidays == null) return;
        ListAdapter adapter =
                new HolidayAdapter(this, holidays);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            HolidayAdapter e = (HolidayAdapter) adapterView.getAdapter();
            Holiday currentHoliday=e.getItem(i);
            toHolidayActivity(currentHoliday);
        });
    }

    private void toHolidayActivity(Holiday holiday){
        Intent intent=new Intent(this,HolidayActivity.class);
        intent.putExtra(HOLIDAY,holiday);
        startActivity(intent);
    }





}

