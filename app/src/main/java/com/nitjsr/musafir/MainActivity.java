package com.nitjsr.musafir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    final int random = new Random().nextInt(2000);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        findViewById(R.id.startRide).setOnClickListener(this);
        findViewById(R.id.busStopMap).setOnClickListener(this);
        findViewById(R.id.searchBus).setOnClickListener(this);
        findViewById(R.id.trackBus).setOnClickListener(this);
        findViewById(R.id.myTickets).setOnClickListener(this);
        findViewById(R.id.emergency).setOnClickListener(this);

        preferences = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        if (!preferences.contains("cid")){
            editor = preferences.edit();
            editor.putString("cid",String.valueOf(random));
            editor.apply();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startRide:
                startActivity(new Intent(this,StartRide.class));
                break;
            case R.id.busStopMap:
                startActivity(new Intent(this,BusStopMap.class));
                break;
            case R.id.searchBus:
                startActivity(new Intent(this,SearchBuses.class));
                break;
            case R.id.myTickets:
                startActivity(new Intent(this,MyTickets.class));
                break;
            case R.id.emergency:
                startActivity(new Intent(this,Emergency.class));
                break;
            case R.id.trackBus:
                startActivity(new Intent(this,SearchBusNo.class));
                break;

        }
    }
}