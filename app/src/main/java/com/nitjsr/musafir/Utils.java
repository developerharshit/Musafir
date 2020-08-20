package com.nitjsr.musafir;

import java.util.Map;

public class Utils {

    public static String[] BusStopNames = {
            "TATANAGAR JN",
            "SONARI",
            "JUGSALAI",
            "SAKCHI",
            "BARIDIH",
            "MANGO",
            "AGRICO",
            "NATRAJ",
            "TELCO",
            "NILDIH",
            "GOLMURI",
            "BISTUPUR",
            "AAKASHVANI",
            "ADITYAPUR",
            "GAMHARIA"
    };
    public static Map<String,Integer> NATRAJ = Map.of(
            "TATANAGAR JN",0,
            "SONARI",3,
            "JUGSALAI",5,
            "SAKCHI",7,
            "BARIDIH",10,
            "MANGO",14,
            "AGRICO",16,
            "NATRAJ",20
            );
    public static Map<String,Integer> TELCO = Map.of(
            "TELCO",0,
            "NILDIH",3,
            "GOLMURI",5,
            "SAKCHI",10,
            "BISTUPUR",12,
            "AAKASHVANI",14,
            "ADITYAPUR",16,
            "GAMHARIA",20
    );

}
