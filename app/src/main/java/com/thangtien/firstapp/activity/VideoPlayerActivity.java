package com.thangtien.firstapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.thangtien.firstapp.R;
import com.thangtien.firstapp.ultil.Constants;

public class VideoPlayerActivity extends AppCompatActivity {
    public static String TAG = "VideoPlayerActivity";
    VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        mVideoView = findViewById(R.id.videoView);

        String path = getIntent().getStringExtra(Constants.KEY_VIDEO_PATH);
        Log.i(TAG, "path: " + path);

        // Setting the video URI to the VideoView
        mVideoView.setVideoPath(path);

        // Creating an object of MediaController class
        MediaController mediaController = new MediaController(this);

        // Setting the anchor view for the MediaController
        mediaController.setAnchorView(mVideoView);

        // Associating the MediaController with the VideoView
        mVideoView.setMediaController(mediaController);

        // Starting the video playback
        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.start();
    }
}