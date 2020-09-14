package com.autoe.autoecustomer.CommonData;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.autoe.autoecustomer.Model.Booking;
import com.autoe.autoecustomer.Model.User;

import java.util.Calendar;
import java.util.Locale;

public class Common {
    public static User currentUser;

    public static Booking currentBooking;
    private static final String BASE_URL="https://fcm.googleapis.com/";
    /*public static APIService getFCMService()
    {
        return FCMRetrofitClient.getClient(BASE_URL).create(APIService.class);
    }*/
    public static final String HATCH_BACK= "Hatch Back";
    public static final String XUV= "XUV";
    public static final String SEDAN= "Sedan";
    //public static final String PWD_KEY= "Password";

    public static String selectedCarType="";
    public static String convertCodeToStatus(String status) {

        if(status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "On my way";
        else
            return "Shipped";
    }
    /*public static boolean isConnectedToInternet(Context context)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null)
        {
            NetworkInfo[] info= connectivityManager.getAllNetworkInfo();
            if(info!=null)
            {
                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }

        }
        return false;
    }
*/
    public static String getDate(long time)
    {
        Calendar calendar= Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        return android.text.format.DateFormat.format("dd-MM-yyyy HH:mm", calendar).toString();
    }

    public static String convertIndexToKg(int index)
    {
        if(index==0)
            return "-";
        else if(index==1)
            return "-";
        else
            return "-";
    }
}
