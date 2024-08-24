package com.thangtien.firstapp.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.thangtien.firstapp.R;
import com.thangtien.firstapp.adapter.ObjectDataAdapter;
import com.thangtien.firstapp.adapter.ProductTypeAdapter;
import com.thangtien.firstapp.adapter.WifiListAdapter;
import com.thangtien.firstapp.model.ObjectData;
import com.thangtien.firstapp.model.ProductType;
import com.thangtien.firstapp.services.ApiService;
import com.thangtien.firstapp.services.MemoryCheckService;
import com.thangtien.firstapp.ultil.Constants;
import com.thangtien.firstapp.ultil.FileUtil;
import com.thangtien.firstapp.ultil.NetworkUtil;
import com.thangtien.firstapp.ultil.WifiManagerClone;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    ProductTypeAdapter productTypeAdapter;
    ObjectDataAdapter objectDataAdapter;
    ArrayList<ProductType> listProductType;
    ArrayList<ObjectData> listObjectData;
    TextView txtSpMoiNhat;
    WifiManagerClone wifiManagerClone;
    String TAG;
    ProgressDialog mProgressDialog;
    Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TAG = "MainActivity";
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        wifiManagerClone.getAvailableWifiNetworks();
        FileUtil.getImagesAndVideos(this);
        FileUtil.getImagesFromInternalStorage(getApplicationContext());
        Log.i(TAG, "isNetworkConnected: " + NetworkUtil.isNetworkConnected(this));
        if (!FileUtil.isServiceRunning(this, MemoryCheckService.class)) {
            Intent serviceIntent = new Intent(this, MemoryCheckService.class);
            startService(serviceIntent);
        }
        if (NetworkUtil.isNetworkConnected(this)) {
            actionBar();
            actionViewFillper();
        }

        txtSpMoiNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_SHOW_APP_INFO);
//                intent.setData(Uri.parse("package:com.thangtien.firstapp.activity"));
//                startActivity(intent);
                mProgressDialog.show();
                ApiService.apiService.getPosts()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                mDisposable = d;
                            }

                            @Override
                            public void onNext(@NonNull List<ObjectData> objectData) {
                                listObjectData = (ArrayList<ObjectData>) objectData;
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.i(TAG, "call api error");
                            }

                            @Override
                            public void onComplete() {
                                Log.i(TAG, "complete call api size:" + listObjectData.size());
                                if (!listObjectData.isEmpty()) {
                                    for (ObjectData objectData : listObjectData)
                                        Log.i(TAG, "objectData: " + objectData.getTitle());
                                }
                                mProgressDialog.dismiss();
                                objectDataAdapter = new ObjectDataAdapter(listObjectData);
                                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                                dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getApplicationContext(), R.drawable.item_divider)));
                                recyclerViewManHinhChinh.addItemDecoration(dividerItemDecoration);
                                recyclerViewManHinhChinh.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
                                recyclerViewManHinhChinh.setAdapter(objectDataAdapter);
                            }
                        });

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    private void showWifiListDialog() {
//        if (!isFinishing()) {
//            Log.i(TAG, "showWifiListDialog");
//            wifiListDialog.show();
//        }
        // Mảng dữ liệu cho ListView
        ArrayList<String> wifiList = new ArrayList<>();
        wifiList.add("ABC");
        wifiList.add("DEF");

        // Tạo AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a wifi to connect");

        // Thiết lập ListView
        ListView listView = new ListView(this);
        WifiListAdapter wifiListAdapter = new WifiListAdapter(this, wifiList);
        listView.setAdapter(wifiListAdapter);

        // Đặt ListView vào AlertDialog
        builder.setView(listView);

        // Tạo và hiển thị AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void actionViewFillper() {
        Log.i(TAG, "actionViewFillper");
        List<String> mangQuangCao = FileUtil.readFromFile(this, Constants.file_name_quang_cao);
        if (mangQuangCao.isEmpty()) return;

        for (String s : mangQuangCao) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(s).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
        viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "click ViewFlipper");

            }
        });
    }

    @SuppressLint("RestrictedApi")
    private void actionBar() {
        Log.i(TAG, "actionBar");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick");
                drawerLayout.openDrawer(GravityCompat.START);
                //wifiManagerClone.showWifiSettings();
            }
        });

        //Đổ dữ liệu vào
        listProductType = FileUtil.readXmlFile(this, Constants.file_product_type);
        productTypeAdapter = new ProductTypeAdapter(this, listProductType);
        listViewManHinhChinh.setAdapter(productTypeAdapter);

        //click ListView
        listViewManHinhChinh.setOnItemClickListener((adapterView, view, i, l) -> {
            ProductType productType = listProductType.get(i);
            FileUtil.toast_short(getApplicationContext(), productType.getImage());
        });
    }

    private void anhXa() {
        toolbar = findViewById(R.id.mainToolBar);
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerViewManHinhChinh = findViewById(R.id.recyclerView);
        navigationView = findViewById(R.id.navigationView);
        listViewManHinhChinh = findViewById(R.id.listViewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerLayout);
        listProductType = new ArrayList<>();
        txtSpMoiNhat = findViewById(R.id.txtSanphammoinhat);
        wifiManagerClone = new WifiManagerClone(this);
        mProgressDialog = new ProgressDialog(this);
        listObjectData = new ArrayList<>();
    }
}