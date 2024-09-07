package com.thangtien.firstapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.thangtien.firstapp.activity.fragments.FragmentsFavorite;
import com.thangtien.firstapp.activity.fragments.FragmentsHome;
import com.thangtien.firstapp.activity.fragments.FragmentsMyPage;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return switch (position) {
            case 1 -> new FragmentsFavorite();
            case 2 -> new FragmentsMyPage();
            default -> new FragmentsHome();
        };
    }

    @Override
    public int getCount() {
        return 3;
    }
}
