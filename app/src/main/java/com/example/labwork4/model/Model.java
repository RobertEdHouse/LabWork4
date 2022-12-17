package com.example.labwork4.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.example.labwork4.BuildConfig;
import com.example.labwork4.viewmodel.HolidayViewModel;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

public class Model {
    private List<Holiday>listHoliday=new ArrayList<>();
    private HolidayViewModel viewModel;
    private Context context;

    public Model(HolidayViewModel viewModel, Context context) {
        this.viewModel = viewModel;
        this.context = context;
    }

    public List<Holiday> getListHoliday(){
        return listHoliday;
    }

    public void update(){
        if(!isNetworkAvailable()){
            loadDB save=new loadDB();
            save.execute();
            return;
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(getLoggingInterceptor())
                .build();
        Request request = new Request.Builder()
                .get()
                .url("https://date.nager.at/api/v3/publicholidays/2021/UA")
                .header("Accept", "application/json")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadDB save=new loadDB();
                save.execute();
            }
            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    String s = body.string();
                    saveJson save=new saveJson(s);
                    save.execute();
                }
            }
        });

    }

    class saveJson extends  AsyncTask<Void, Integer, Boolean>{
        private String json;
        public saveJson(String json){
            this.json=json;
        }

        @Override
        protected Boolean doInBackground(Void... unused) {
            try {
                Moshi moshi = new Moshi.Builder().build();
                Type listMyData = Types.newParameterizedType(List.class, Holiday.class);
                JsonAdapter<List<Holiday>> jsonAdapter = moshi.adapter(listMyData);
                listHoliday = jsonAdapter.fromJson(json);
                Work work=new Work(context);
                work.saveInDB(listHoliday);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean==true)
                viewModel.update(false);
        }
    }

    class loadDB extends  AsyncTask<Void, Integer, Boolean>{
        @Override
        protected Boolean doInBackground(Void... unused) {
                Work work=new Work(context);
                listHoliday = work.loadFromDB();
                return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean==true)
                viewModel.update(false);
        }
    }


    public void setContext(Context context){
        this.context=context;
    }

    private HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }
        return httpLoggingInterceptor;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



}

