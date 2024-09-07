package com.thangtien.firstapp.activity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.thangtien.firstapp.R;
import com.thangtien.firstapp.adapter.VideoAdapter;
import com.thangtien.firstapp.model.Video;
import com.thangtien.firstapp.ultil.FileUtil;

import java.util.List;

public class GetVideoFromGalleryActivity extends AppCompatActivity {
    private RecyclerView recVideo;
    private VideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_video_from_gallery);
        Button btnGetVideo = findViewById(R.id.btnGetVideo);
        recVideo = findViewById(R.id.rec_video);

        videoAdapter = new VideoAdapter(getApplicationContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recVideo.setLayoutManager(gridLayoutManager);

        btnGetVideo.setOnClickListener(v -> checkPermission());

    }

    private void checkPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                FileUtil.toast_short(getApplicationContext(), "Permission Granted");
                getAllVideoFromGallery();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                FileUtil.toast_short(getApplicationContext(), "Permission Denied\n" + deniedPermissions.toString());
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_MEDIA_VIDEO)
                .check();
    }

    private void getAllVideoFromGallery() {
        List<Video> listVideo = FileUtil.getAllVideoFromGallery(getApplicationContext());
        videoAdapter.setData(listVideo);
        recVideo.setAdapter(videoAdapter);

    }
}