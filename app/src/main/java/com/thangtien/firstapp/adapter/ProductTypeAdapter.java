package com.thangtien.firstapp.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thangtien.firstapp.R;
import com.thangtien.firstapp.model.ProductType;

import java.util.ArrayList;

public class ProductTypeAdapter extends BaseAdapter {
    Context context;
    ArrayList<ProductType> listProductType;

    public ProductTypeAdapter(Context context, ArrayList<ProductType> listProductType) {
        this.context = context;
        this.listProductType = listProductType;
    }

    @Override
    public int getCount() {
        return listProductType.size();
    }

    @Override
    public Object getItem(int i) {
        return listProductType.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_product_type, viewGroup, false);
        }
        ImageView imageView = view.findViewById(R.id.image_item_product_type);
        TextView textView = view.findViewById(R.id.name_product_type);
        ProductType productType = listProductType.get(i);
        textView.setText(productType.getName());

        Glide.with(context).load(productType.getImage())
                .placeholder(android.R.drawable.alert_dark_frame)
                .error(android.R.drawable.ic_delete)
                .into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        return view;
    }
}
