package com.nitjsr.musafir;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @POST("availableBuses")
    Call<List<Bus>> getAllBusStops(
            @Query("Src") String src,
            @Query("Dst") String dst
    );
    @GET("bus/{id}")
    Call<Bus> getBusbyId(@Path("id") int busId);

    @POST("ticket/create")
    Call<Ticket> createTicket(@Body Ticket ticket);

    @GET("customerticket?")
    Call<List<Ticket>> getAllTickets(
            @Query("customerId") int custId
    );


    @FormUrlEncoded
    @POST("upload")
    Call<Mask> checkMask(@FieldMap Map<String,String> params);
}
