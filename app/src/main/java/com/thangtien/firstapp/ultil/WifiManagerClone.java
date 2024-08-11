package com.thangtien.firstapp.ultil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class WifiManagerClone {
    static String TAG = "WifiManagerClone";

    private Context mContext;
    private WifiManager mWifiManager;

    public WifiManagerClone(Context context) {
        this.mContext = context;
        mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    // Hiển thị màn hình cài đặt Wi-Fi để kết nối khi không có kết nối mạng
    public void showWifiSettings() {
        Log.i(TAG, "showWifiSettings");
        mContext.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

    // Lấy danh sách các mạng Wi-Fi
    public List<ScanResult> getWifiNetworks() {
        List<ScanResult> result = new ArrayList<>();
        if (mWifiManager != null) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return result;
            }
            result = mWifiManager.getScanResults();
        }
        return result;
    }

    // Kết nối đến một mạng Wi-Fi cụ thể
    public void connectToWifi(String ssid, String password) {
        if (mWifiManager != null) {
            WifiConfiguration wifiConfig = new WifiConfiguration();
            wifiConfig.SSID = String.format("\"%s\"", ssid);
            wifiConfig.preSharedKey = String.format("\"%s\"", password);

            int netId = mWifiManager.addNetwork(wifiConfig);
            mWifiManager.disconnect();
            mWifiManager.enableNetwork(netId, true);
            mWifiManager.reconnect();
        }
    }

    public ArrayList<ScanResult> getAvailableWifiNetworks() {
        ArrayList<ScanResult> scanResults = new ArrayList<>();
        // Kiểm tra xem Wi-Fi đã bật chưa
        if (!mWifiManager.isWifiEnabled()) {
            // Bật Wi-Fi nếu chưa được bật
            mWifiManager.setWifiEnabled(true);
        }
        // Bắt đầu quét mạng Wi-Fi
        mWifiManager.startScan();

        // Lấy danh sách các mạng Wi-Fi có thể kết nối được
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            scanResults = (ArrayList<ScanResult>) mWifiManager.getScanResults();
        }
        Log.i(TAG, "scanResults: " + scanResults.size());
        return scanResults;
    }
}

