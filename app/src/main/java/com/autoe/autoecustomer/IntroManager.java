package com.autoe.autoecustomer;

import android.content.Context;
import android.content.SharedPreferences;

public class IntroManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    //@param pref - to get value from memory
    //@param editor - to edit2 value from memory stored by sharedPreferences
    //@param context - storing current instance

    public IntroManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences("first",0);
        editor=pref.edit();
    }

    public void setFirst(Boolean isFirst){
        editor.putBoolean("check",isFirst);
        editor.commit();
    }

    public boolean check(){
        return pref.getBoolean("check",false);
    }
}
