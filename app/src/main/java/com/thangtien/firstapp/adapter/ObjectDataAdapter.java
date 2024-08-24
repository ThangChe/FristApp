package com.thangtien.firstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thangtien.firstapp.R;
import com.thangtien.firstapp.model.ObjectData;

import java.util.ArrayList;

public class ObjectDataAdapter extends RecyclerView.Adapter<ObjectDataAdapter.DataViewHodler> {
    ArrayList<ObjectData> list;

    public ObjectDataAdapter(ArrayList<ObjectData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DataViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_objectdata_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHodler holder, int position) {
        ObjectData objectData = list.get(position);
        holder.title.setText(objectData.getTitle());
        holder.body.setText(objectData.getBody());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DataViewHodler extends RecyclerView.ViewHolder {
        TextView title, body;

        public DataViewHodler(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_title);
            body = itemView.findViewById(R.id.txt_body);
        }
    }

}
