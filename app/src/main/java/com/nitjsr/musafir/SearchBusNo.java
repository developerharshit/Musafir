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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchBusNo extends AppCompatActivity {

    TextView name,no,source,dest,loc,seats;
    EditText busId;
    Button searchBus;
    ProgressDialog progressDialog;
    int id;
    Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bus_no);

        progressDialog = new ProgressDialog(this);
        busId = findViewById(R.id.busId);
        searchBus = findViewById(R.id.showBus);

        name = findViewById(R.id.busName);
        no = findViewById(R.id.busNo);
        source = findViewById(R.id.busSource);
        dest = findViewById(R.id.busDest);
        seats = findViewById(R.id.busSeats);
        loc = findViewById(R.id.busLoc);

        setRetrofit();
        searchBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(busId);
                if(!(TextUtils.isEmpty(busId.getText()))){
                    id = Integer.parseInt(busId.getText().toString());
                    getBus();
                }
            }
        });
    }
    private void setRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://morning-stream-99861.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    private void getBus() {
        progressDialog.show();
        progressDialog.setMessage("Please wait");
        Call<Bus> call = api.getBusbyId(id);

        call.enqueue(new Callback<Bus>() {
            @Override
            public void onResponse(Call<Bus> call, Response<Bus> response) {
                if(response.isSuccessful()){
                    if(response.body()==null){
                        Toast.makeText(SearchBusNo.this, "Invalid Bus Number", Toast.LENGTH_SHORT).show();
                        addBusData(new Bus());
                    }
                    else{
                        Bus data = response.body();
                        addBusData(data);
                    }
                    progressDialog.dismiss();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(SearchBusNo.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Bus> call, Throwable t) {
                Toast.makeText(SearchBusNo.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("hello",""+t.getMessage());
            }
        });
    }

    private void addBusData(Bus bus) {
        name.setText(bus.getBUS_NAME());
        no.setText(String.valueOf(bus.getBUS_ID()));
        source.setText(bus.getSRC());
        dest.setText(bus.getDST());
        loc.setText(bus.getCURRENT_LOCATION());
        seats.setText(String.valueOf(bus.getSEATS_AVAILABLE()));
    }
    private void hideKeyboard(View v){
        v.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}