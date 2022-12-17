package com.example.labwork4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.labwork4.model.Holiday;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HolidayActivity extends AppCompatActivity {

    private final String HOLIDAY="holiday";
    private Holiday currentHoliday=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday);
        currentHoliday= (Holiday) getIntent().getExtras().getSerializable(HOLIDAY);
        TextView localNameView = findViewById(R.id.localTextView);
        TextView nameView = findViewById(R.id.nameTextView);
        TextView dateView = findViewById(R.id.dateTextView);
        TextView yearView = findViewById(R.id.yearTextView);
        if(currentHoliday!=null){
            localNameView.setText(currentHoliday.getLocalName());
            nameView.setText(currentHoliday.getName());
            dateView.setText("Дата "+makeDate(currentHoliday.getDate()));
            yearView.setText(currentHoliday.getLaunchYear());
        }
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ActionBar actionBar = getSupportActionBar();
            assert actionBar != null;
            actionBar.setDisplayHomeAsUpEnabled(false);
            toMainActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void toMainActivity(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private String makeDate(String date){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-mm-dd");
        try {
            Date docDate= format.parse(date);
            format.applyPattern("dd.mm.yyyy");
            return format.format(docDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}