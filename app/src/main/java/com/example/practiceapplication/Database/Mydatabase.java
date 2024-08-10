package com.example.practiceapplication.Database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Mydatabase extends SQLiteAssetHelper {

    public final static String DB_NAME = "cars.db";
    public final static int DB_VERSION = 1;
    public final static String CAR_TAB_NAME = "car";
    public final static String CAR_TAB_ID = "id";
    public final static String CAR_TAB_MODEL = "model";
    public final static String CAR_TAB_COLOR = "color";
    public final static String CAR_TAB_description = "description";
    public final static String CAR_TAB_image = "images";
    public final static String CAR_TAB_DPL = "distancePerLetter";


    public Mydatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }






}
