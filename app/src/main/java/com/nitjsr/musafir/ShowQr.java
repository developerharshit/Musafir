package com.nitjsr.musafir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ShowQr extends AppCompatActivity {

    String data;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qr);

        image = findViewById(R.id.image);

        if(getIntent().hasExtra("ticket")){
            data = getIntent().getStringExtra("ticket");
        }

        Picasso.with(this).load("https://api.qrserver.com/v1/create-qr-code/?data=" +data+ "&size=240x240&margin=10")
                .placeholder(R.drawable.placeholder).fit().networkPolicy(NetworkPolicy.OFFLINE).into(image, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(ShowQr.this).load("https://api.qrserver.com/v1/create-qr-code/?data=" +data+"&size=240x240&margin=10")
                        .placeholder(R.drawable.placeholder).fit().into(image);
            }
        });
    }
}