package com.nitjsr.musafir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyTickets extends AppCompatActivity {

    Api api;
    List<Ticket> data;
    ProgressDialog progressDialog;
    String cid="3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");

        setRetrofit();
        getAllTickets();

//        SharedPreferences preferences = getSharedPreferences(MainActivity.MY_PREFS_NAME,MODE_PRIVATE);
//        cid = preferences.getString("cid","3");
    }
    private void getAllTickets() {
        progressDialog.show();
        progressDialog.setMessage("Please wait");
        Call<List<Ticket>> call = api.getAllTickets(Integer.parseInt(cid));

        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    data = response.body();
                    createTicketList();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(MyTickets.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                Toast.makeText(MyTickets.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("hello",""+t.getMessage());
            }
        });
    }
    private void createTicketList() {
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        TicketAdapter adapter = new TicketAdapter(this, (ArrayList<Ticket>) data);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }
    private void setRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://morning-stream-99861.herokuapp.com/api/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }
}