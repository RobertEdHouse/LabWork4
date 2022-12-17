package com.example.labwork4.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.labwork4.R;
import com.example.labwork4.model.Holiday;
import com.squareup.moshi.FromJson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HolidayAdapter extends BaseAdapter {
    private final Context context;
    private final List<Holiday> holidays;

    public HolidayAdapter(Context context, List<Holiday> holidays) {
        this.context = context;
        this.holidays = holidays;
    }

    @Override
    public int getCount() {
        return holidays.size();
    }

    @Override
    public Holiday getItem(int i) {
        return holidays.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.adapter_view, parent, false);
            holder = new ViewHolder();
            holder.dateTextView = convertView
                    .findViewById(R.id.dateTextView);
            holder.nameTextView = convertView
                    .findViewById(R.id.nameTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Holiday holiday = getItem(position);
        String time = holiday.getLocalName();
        String date = holiday.getDate();
        holder.nameTextView.setText(time);
        holder.dateTextView.setText(makeDate(date));
        return convertView;
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

    static class ViewHolder {
        TextView dateTextView;
        TextView nameTextView;
    }

}
