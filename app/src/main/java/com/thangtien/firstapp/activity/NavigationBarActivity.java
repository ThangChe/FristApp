package com.thangtien.firstapp.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thangtien.firstapp.R;
import com.thangtien.firstapp.adapter.ViewPagerAdapter;
import com.thangtien.firstapp.ultil.FileUtil;

public class NavigationBarActivity extends AppCompatActivity {
    private BottomNavigationView mNavigationView;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_navigation_bar);

        mNavigationView = findViewById(R.id.bottom_navigation);
        mViewPager = findViewById(R.id.view_pager_navi);

        setUpViewPager();
        mNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_home) {
                FileUtil.toast_short(getApplicationContext(), "Home");
                mViewPager.setCurrentItem(0);
            } else if (item.getItemId() == R.id.action_favorite) {
                FileUtil.toast_short(getApplicationContext(), "Favorite");
                mViewPager.setCurrentItem(1);
            } else if (item.getItemId() == R.id.action_my_page) {
                FileUtil.toast_short(getApplicationContext(), "My page");
                mViewPager.setCurrentItem(2);
            }

            return true;
        });

    }

    private void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1:
                        mNavigationView.getMenu().findItem(R.id.action_favorite).setChecked(true);
                        break;
                    case 2:
                        mNavigationView.getMenu().findItem(R.id.action_my_page).setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}