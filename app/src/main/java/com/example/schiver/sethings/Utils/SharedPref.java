package com.example.schiver.sethings.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    // Save Shared Preference
    public static void saveSharefPref(Context context, String sharedPrefName , String value){
        SharedPreferences mySharedPref = context.getSharedPreferences("SeThings",Context.MODE_PRIVATE);
        SharedPreferences.Editor edtSharedPref  = mySharedPref.edit();
        edtSharedPref.putString(sharedPrefName,value);
        edtSharedPref.apply();
    }

    // Get Shared Preference
    public static String readSharedPref(Context context, String sharedPrefName , String defaultValue){
        SharedPreferences mySharedPref = context.getSharedPreferences("SeThings",Context.MODE_PRIVATE);
        return mySharedPref.getString(sharedPrefName,defaultValue);
    }

}
