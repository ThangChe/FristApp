package com.thangtien.firstapp.adapter;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thangtien.firstapp.R;
import com.thangtien.firstapp.activity.VideoPlayerActivity;
import com.thangtien.firstapp.model.Video;
import com.thangtien.firstapp.ultil.Constants;
import com.thangtien.firstapp.ultil.FileUtil;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    Context context;
    List<Video> list;

    public VideoAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Video> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Video video = list.get(position);
        if (video == null) {
            return;
        }

        Glide.with(context).load(video.getThumb()).into(holder.imageView);

        //click item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FileUtil.toast_short(context, "vi tri: " + position);
                Video mVideo = list.get(position);
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra(Constants.KEY_VIDEO_PATH, mVideo.getPath());
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_video);
        }
    }
}
