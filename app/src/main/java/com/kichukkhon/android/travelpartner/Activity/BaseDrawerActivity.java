package com.kichukkhon.android.travelpartner.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kichukkhon.android.travelpartner.R;

/**
 * Created by Ratul on 8/25/2016.
 */
public class BaseDrawerActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private int mNavItemId;

    private static final String NAV_ITEM_ID = "navItemId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            //mNavItemId = R.id.menu_home;
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }
    }

    public void InitCommonUIElements() {
        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarWithAppbar);
        setSupportActionBar(toolbar);

        // Create Navigation drawer and inflate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator
                    = VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(), R.color.white, getTheme()));
            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);
                        mNavItemId = menuItem.getItemId();

                        if (menuItem.getItemId() == R.id.menu_home) {
                            Intent intent = new Intent(BaseDrawerActivity.this, TourListActivity.class);
                            startActivity(intent);
                        } else if (menuItem.getItemId() == R.id.menu_expense) {
                            Intent intent = new Intent(BaseDrawerActivity.this, ExpenseInfoActivity.class);
                            startActivity(intent);
                        } else if (menuItem.getItemId() == R.id.menu_note) {
                            Intent intent = new Intent(BaseDrawerActivity.this, NoteListActivity.class);
                            startActivity(intent);
                        } else if (menuItem.getItemId() == R.id.menu_alarm) {
                            Intent intent = new Intent(BaseDrawerActivity.this, AlarmActivity.class);
                            startActivity(intent);
                        } else if (menuItem.getItemId() == R.id.menu_weather) {
                            Intent intent = new Intent(BaseDrawerActivity.this, WeatherActivity.class);
                            startActivity(intent);
                        } else if (menuItem.getItemId() == R.id.menu_nearby) {
                            Intent intent = new Intent(BaseDrawerActivity.this, NearbyPlacesHomeActivity.class);
                            startActivity(intent);
                        } else if (menuItem.getItemId() == R.id.menu_route_tracker) {
                            Intent intent = new Intent(BaseDrawerActivity.this, RouteListActivity.class);
                            startActivity(intent);
                        }

                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
        navigationView.getMenu().findItem(mNavItemId).setChecked(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }
}
