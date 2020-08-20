package com.nitjsr.musafir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchBuses extends AppCompatActivity {

    Api api;
    ProgressDialog progressDialog;
    AutoCompleteTextView dest,sour;
    String source,destination;
    Button searchBus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_buses);

        progressDialog = new ProgressDialog(this);

        sour = findViewById(R.id.source);
        dest = findViewById(R.id.dest);
        searchBus = findViewById(R.id.showBus);

        searchBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(destination) && !TextUtils.isEmpty(source) && !destination.equals(source)){
                    getBuses();
                }
                else{
                    Toast.makeText(SearchBuses.this, "Enter correct source and destination", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Toast.makeText(this, "Scan the QR Code from Bus Stop", Toast.LENGTH_LONG).show();
        setAutoCompleteTextView();
        setRetrofit();
    }

    private void getBuses() {
        progressDialog.show();
        progressDialog.setMessage("Please wait");
        Call<List<Bus>> call = api.getAllBusStops(source,destination);

        call.enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    List<Bus> data = response.body();
                    createBusList(data);
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(SearchBuses.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                Toast.makeText(SearchBuses.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("hello",""+t.getMessage());
            }
        });
    }

    private void createBusList(List<Bus> data) {
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        BusAdapter adapter = new BusAdapter(this, (ArrayList<Bus>) data,false);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }

    private void setAutoCompleteTextView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line, Utils.BusStopNames);
        dest.setThreshold(1);
        dest.setAdapter(adapter);
        sour.setThreshold(1);
        sour.setAdapter(adapter);

        sour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sour.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(dest.getWindowToken(), 0);
                source = adapterView.getItemAtPosition(i).toString().trim();
            }
        });


        dest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dest.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(dest.getWindowToken(), 0);
                destination = adapterView.getItemAtPosition(i).toString().trim();
            }
        });
    }

    private void setRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://morning-stream-99861.herokuapp.com/api/v2/busStop/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }
}