package com.nitjsr.musafir;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nitjsr.musafir.Paytm.PaytmPayments;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentGateway extends AppCompatActivity{

    int price;
    Api api;
    ProgressDialog progressDialog;
    int position;
    String s,d;
    String cid = "3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        progressDialog = new ProgressDialog(this);

        if(getIntent().hasExtra("position")){
            position = getIntent().getIntExtra("position",0);
        }
        if(getIntent().hasExtra("price")){
            price = getIntent().getIntExtra("price",0);
        }
        if(getIntent().hasExtra("source")){
            s = getIntent().getStringExtra("source");
        }
        if(getIntent().hasExtra("destination")){
            d = getIntent().getStringExtra("destination");
        }

//        SharedPreferences preferences = getSharedPreferences(MainActivity.MY_PREFS_NAME,MODE_PRIVATE);
//        cid = preferences.getString("cid","2");


        setRetrofit();

        findViewById(R.id.pay).setOnClickListener(view -> {
//            PaytmPayments paytmPayments = new PaytmPayments(this);
//            paytmPayments.initiatePayment("100");

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Transaction Amount: ₹"+price);
            dialog.setMessage("Are you sure?");
            dialog.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                createTicket();
            });
            dialog.setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });

            dialog.show();
        });
    }
    private void setRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://morning-stream-99861.herokuapp.com/api/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }
    private void createTicket() {
        progressDialog.show();
        progressDialog.setMessage("Please wait");

        Ticket ticket = new Ticket(
                s,
                d,
                "halfjlasfkj",
                Integer.parseInt(cid),
                StartRide.data.get(position).getBUS_ID(),
                price
        );

        Call<Ticket> call = api.createTicket(ticket);

        call.enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(PaymentGateway.this, "Ticket Booked Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(PaymentGateway.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {
                Toast.makeText(PaymentGateway.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("hello",""+t.getMessage());
            }
        });
    }

}