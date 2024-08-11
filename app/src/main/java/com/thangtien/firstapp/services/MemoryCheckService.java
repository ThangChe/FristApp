package com.thangtien.firstapp.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.StatFs;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.thangtien.firstapp.R;
import com.thangtien.firstapp.ultil.Constants;
import com.thangtien.firstapp.ultil.FileUtil;
import com.thangtien.firstapp.ultil.NetworkUtil;

import java.io.File;

public class MemoryCheckService extends Service {
    private static final String TAG = "MemoryCheckService";

    // Hằng số cho khoảng thời gian giữa các lần kiểm tra (5 giây)
    private static final long DELAY = 5000;

    // Handler để thực hiện công việc kiểm tra bộ nhớ
    private Handler mHandler;
    private int numberCheck = 0;

    // Runnable để thực hiện công việc trong mỗi vòng lặp
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            // Lấy bộ nhớ còn lại của ứng dụng
            long memory = getAvailableInternalMemorySize();

            // In ra kết quả
            Log.d(TAG, "Memory available: " + memory + " bytes");

            // Kiểm tra kết nối mạng
            if (numberCheck == 0 && !NetworkUtil.isNetworkConnected(getApplicationContext())) {
                FileUtil.toast_short(getApplicationContext(), Constants.message_khong_ket_noi_mang);
                numberCheck++;
                NetworkUtil.startSettingsWifi(getApplicationContext());
            } else {
                if (numberCheck != 0 && NetworkUtil.isNetworkConnected(getApplicationContext())) {
                    FileUtil.toast_short(getApplicationContext(), Constants.message_ket_noi_mang_thanh_cong);
                    numberCheck = 0;
                    NetworkUtil.startMainActivity(getApplicationContext());
                }
            }

            // Lặp lại việc kiểm tra sau một khoảng thời gian DELAY
            mHandler.postDelayed(this, DELAY);
        }
    };

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        super.onCreate();
        mHandler = new Handler(Looper.getMainLooper());
        startForeground(123, createNotification());
    }

    private Notification createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("123",
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "123")
                .setContentTitle("Foreground Service")
                .setContentText("Service is running in the foreground")
                .setSmallIcon(R.drawable.ic_launcher_background);

        return builder.build();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        // Bắt đầu công việc kiểm tra bộ nhớ
        mHandler.postDelayed(mRunnable, DELAY);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Dừng việc kiểm tra bộ nhớ khi dịch vụ bị hủy
        mHandler.removeCallbacks(mRunnable);
    }

    // Phương thức để lấy bộ nhớ còn lại của ứng dụng
    private long getAvailableInternalMemorySize() {
        File path = getFilesDir();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return availableBlocks * blockSize;
    }
}

