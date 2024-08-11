package com.thangtien.firstapp.ultil;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

import com.thangtien.firstapp.activity.MainActivity;

public class NetworkUtil {
    public static String TAG = "NetworkUtil";

    public static boolean isNetworkConnected(Context context) {
        Log.i(TAG, "isNetworkConnected");
        boolean isConnectedMobile = false;
        boolean isConnectedWifi = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] listNetwork = cm.getAllNetworkInfo();
        for (NetworkInfo networkInfo : listNetwork) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE && networkInfo.isConnected()) {
                isConnectedMobile = true;
            } else {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected()) {
                    isConnectedWifi = true;
                }
            }
        }
        return isConnectedWifi || isConnectedMobile;
    }

    public static void startSettingsWifi(Context context) {
        Log.i(TAG, "startSettingsWifi");
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startMainActivity(Context context) {
        Log.i(TAG, "startMainActivity");
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }
}

