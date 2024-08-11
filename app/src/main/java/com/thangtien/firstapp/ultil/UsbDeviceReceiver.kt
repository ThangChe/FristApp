package com.thangtien.firstapp.ultil

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log
import com.thangtien.firstapp.activity.MainActivity

class UsbDeviceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                val device: UsbDevice? =
                    intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                // Xử lý thiết bị USB được gắn vào
                Log.i(TAG, "ACTION_USB_DEVICE_ATTACHED")
                var name = device?.productName
                FileUtil.toast_short(context, "action ACTION_USB_DEVICE_ATTACHED")
//                val startMainActivity = Intent(context, MainActivity::class.java)
//                startMainActivity.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                context.startService(startMainActivity)
            }

            UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                val device: UsbDevice? =
                    intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                // Xử lý thiết bị USB được rút ra
                Log.i(TAG, "ACTION_USB_DEVICE_DETACHED")
                FileUtil.toast_short(context, "action ACTION_USB_DEVICE_DETACHED")
            }
        }
    }

    companion object {
        var TAG = "UsbDeviceReceiver"
    }
}