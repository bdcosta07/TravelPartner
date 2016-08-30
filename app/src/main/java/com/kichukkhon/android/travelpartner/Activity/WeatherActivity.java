package com.kichukkhon.android.travelpartner.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.kichukkhon.android.travelpartner.Fragment.CurrentWeatherFragment;
import com.kichukkhon.android.travelpartner.Fragment.ForecastWeatherFragment;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Settings.SettingsUtils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class WeatherActivity extends BaseDrawerActivity {

    private static final int NUM_PAGES = 2;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private ImageView[] dots;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        InitCommonUIElements();

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new WeatherPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(mPageChangeListener);

        drawPageSelectionIndicators(0);

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu();

                drawPageSelectionIndicators(position);
            }
        });
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void drawPageSelectionIndicators(int mPosition) {
        if (linearLayout != null) {
            linearLayout.removeAllViews();
        }

        linearLayout = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        dots = new ImageView[NUM_PAGES];
        for (int i = 0; i < NUM_PAGES; i++) {
            dots[i] = new ImageView(this);
            if (i == mPosition) {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
            } else {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);
            linearLayout.addView(dots[i], params);
        }
    }

    private class WeatherPagerAdapter extends FragmentStatePagerAdapter {
        public WeatherPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return ForecastWeatherFragment.newInstance(position);

                default:
                    return CurrentWeatherFragment.create(position);

            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public int getItemPosition(Object object) {
            /*if (object instanceof HourlyWeatherFragment) {
                ((HourlyWeatherFragment) object).getHourlyWeather();
            } else if (object instanceof WeatherPageFragment) {
                ((WeatherPageFragment) object).getCurrentWeather();
            }
            return super.getItemPosition(object);*/
            return PagerAdapter.POSITION_NONE;
        }
    }

    private void notifyViewPagerDataSetChanged() {
        //Log.d(TAG, "\nnotifyDataSetChanged()");
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
