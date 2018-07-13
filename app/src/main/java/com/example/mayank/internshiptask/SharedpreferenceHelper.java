package com.example.mayank.internshiptask;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedpreferenceHelper {
    private static Context mCtx;
    private static SharedpreferenceHelper mInstance;
    public static final String SharedprefenceName = "USER_DATA";

    private SharedpreferenceHelper(Context context) {
        mCtx = context;
    }


    public static synchronized SharedpreferenceHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedpreferenceHelper(context);
        }
        return mInstance;
    }

    public boolean userInfo(String Name,String Email) {

        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();

        editor.putString("name",Name);
        editor.putString("email", Email);


        editor.apply();
        return true;
    }

    public boolean setImage(String image) {

        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();

        editor.putString("image",image);



        editor.apply();
        return true;
    }

    public String getEmail() {
        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);

        return sharedPreference.getString("email", null);

    }

    public String getName() {
        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);

        return sharedPreference.getString("name", null);

    }

    public String getImage() {
        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);

        return sharedPreference.getString("image", null);

    }




}
