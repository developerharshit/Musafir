package com.nitjsr.musafir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Register extends AppCompatActivity {

    String name,email,password,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


}