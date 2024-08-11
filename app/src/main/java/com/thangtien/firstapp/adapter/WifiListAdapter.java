package com.thangtien.firstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.thangtien.firstapp.R;
import com.thangtien.firstapp.ultil.FileUtil;
import com.thangtien.firstapp.ultil.NetworkUtil;

import java.util.ArrayList;

public class WifiListAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> wifiList;

    public WifiListAdapter(Context context, ArrayList<String> wifiList) {
        this.context = context;
        this.wifiList = wifiList;
    }

    @Override
    public int getCount() {
        return wifiList.size();
    }

    @Override
    public Object getItem(int i) {
        return wifiList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_wifilist, viewGroup, false);
        }
        TextView txt = view.findViewById(R.id.txt_name_wifi);
        txt.setText(wifiList.get(i));
        Button btn = view.findViewById(R.id.btnKetNoi);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileUtil.toast_short(context, "Kết nối với wifi ở vị trí số " + i);
                NetworkUtil.startSettingsWifi(context);
            }
        });
        return view;
    }
}
