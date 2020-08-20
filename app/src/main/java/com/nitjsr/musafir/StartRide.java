package com.nitjsr.musafir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StartRide extends AppCompatActivity implements BusAdapter.OnClick{

    Api api;
    ProgressDialog progressDialog;
    AutoCompleteTextView dest;
    TextView sour;
    String source,destination;
    Button searchBus, scan;
    public static List<Bus> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_ride);
        progressDialog = new ProgressDialog(this);

        sour = findViewById(R.id.source);
        dest = findViewById(R.id.dest);
        searchBus = findViewById(R.id.showBus);
        scan = findViewById(R.id.qrScan);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQrCode();
            }
        });
        searchBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(destination) && !TextUtils.isEmpty(source)){
                    getBuses();
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
                    data = response.body();
                    createBusList();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(StartRide.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                Toast.makeText(StartRide.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("hello",""+t.getMessage());
            }
        });
    }

    private void createBusList() {
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        BusAdapter adapter = new BusAdapter(this, (ArrayList<Bus>) data, true);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }

    private void setAutoCompleteTextView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line, Utils.BusStopNames);
        dest.setThreshold(1);
        dest.setAdapter(adapter);

        dest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dest.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(dest.getWindowToken(), 0);

                if(adapterView.getItemAtPosition(i).toString().trim().equals(source)){
                    dest.setError("Source and Destination cannot be same");
                    searchBus.setEnabled(false);
                }
                else{
                    searchBus.setEnabled(true);
                }
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

    public void scanQrCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("");
        integrator.setOrientationLocked(false);
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scanning Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                dest.setEnabled(true);
                scan.setVisibility(View.GONE);
                searchBus.setVisibility(View.VISIBLE);
                sour.setText(result.getContents());
                source = result.getContents();
            }
        } else super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void click(int position) {
        Intent intent = new Intent(this,BookSeat.class);
        intent.putExtra("position", position);
        intent.putExtra("source",source);
        intent.putExtra("destination",destination);
        startActivity(intent);
        finish();
    }
}