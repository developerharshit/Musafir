package com.nitjsr.musafir;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Math.abs;

public class BookSeat extends AppCompatActivity {

    TextView name,no,source,dest,loc,seats,avail,distance,fare,msg;
    Button click,pay;
    Bus bus;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private int position;
    public static int speed = 20;
    String s,d;
    int price;
    Api api;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_seat);

        progressDialog = new ProgressDialog(this);

        name = findViewById(R.id.busName);
        no = findViewById(R.id.busNo);
        source = findViewById(R.id.busSource);
        dest = findViewById(R.id.busDest);
        seats = findViewById(R.id.busSeats);
        loc = findViewById(R.id.busLoc);
        click = findViewById(R.id.clickPhoto);
        avail = findViewById(R.id.avail);
        distance = findViewById(R.id.distance);
        fare = findViewById(R.id.fare);
        pay = findViewById(R.id.pay);
        msg = findViewById(R.id.msg);

        if(getIntent().hasExtra("position")){
            position = getIntent().getIntExtra("position",0);
        }
        if(getIntent().hasExtra("source")){
            s = getIntent().getStringExtra("source");
        }
        if(getIntent().hasExtra("destination")){
            d = getIntent().getStringExtra("destination");
        }

        bus = StartRide.data.get(position);

        click.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        pay.setOnClickListener(view -> {
            Intent intent = new Intent(this,PaymentGateway.class);
            intent.putExtra("price",price);
            intent.putExtra("position",position);
            intent.putExtra("source",s);
            intent.putExtra("destination",d);
            startActivity(intent);
            finish();
        });

        setRetrofit();
//        test();
        setData();
    }

    private void setRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://face-mask-detector.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (photo != null) {
                photo.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
            }
            byte[] b = baos.toByteArray();
            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
            encodedImage = "data:image/jpg"+";base64,"+encodedImage;
            checkMask(encodedImage);
//            test(encodedImage);
        }
    }

    private void checkMask(String image) {
        progressDialog.show();
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        Map<String,String> params = new HashMap<>();
        params.put("image", image);
        Call<Mask> call = api.checkMask(params);
        Log.d("hello",image);

        call.enqueue(new Callback<Mask>() {
            @Override
            public void onResponse(Call<Mask> call, Response<Mask> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    Mask data = response.body();
//                    Toast.makeText(BookSeat.this, data.isFaceMaskDetected()+"", Toast.LENGTH_SHORT).show();
                    if(data.isFaceMaskDetected()){
                        Intent intent = new Intent(BookSeat.this,PaymentGateway.class);
                        intent.putExtra("price",price);
                        intent.putExtra("position",position);
                        intent.putExtra("source",s);
                        intent.putExtra("destination",d);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(BookSeat.this, "Cannot book ticket, please wear a mask", Toast.LENGTH_LONG).show();
                        msg.setText("You cannot proceed\nPlease wear a mask");
                        msg.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    }
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(BookSeat.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Mask> call, Throwable t) {
                Toast.makeText(BookSeat.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("hello",""+t.getMessage());
            }
        });
    }

    private void setData() {
        name.setText(bus.getBUS_NAME());
        no.setText(String.valueOf(bus.getBUS_ID()));
        source.setText(bus.getSRC());
        dest.setText(bus.getDST());
        loc.setText(bus.getCURRENT_LOCATION());
        seats.setText(String.valueOf(bus.getSEATS_AVAILABLE()));
        int p= (bus.getSEATS_AVAILABLE()*100)/bus.getTOTAL_SEATS();
        avail.setText(p+"%");
        int dist=0;
        if(bus.getSRC().equals("TELCO") || bus.getDST().equals("TELCO")){
            dist = abs(Utils.TELCO.get(s)-Utils.TELCO.get(d));
        }
        else if(bus.getSRC().equals("NATRAJ") || bus.getDST().equals("NATRAJ")){
            dist = abs(Utils.NATRAJ.get(s)-Utils.NATRAJ.get(d));
        }
        distance.setText(String.valueOf(dist));
        fare.setText(String.valueOf(dist*10));
        price=dist*10;
    }
}