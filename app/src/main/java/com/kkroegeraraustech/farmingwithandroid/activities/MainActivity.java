package com.kkroegeraraustech.farmingwithandroid.activities;


import android.app.Activity;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.kkroegeraraustech.farmingwithandroid.adapters.Review_ExpandableListAdapter;
import com.kkroegeraraustech.farmingwithandroid.adapters.TabAdapter;
import com.kkroegeraraustech.farmingwithandroid.adapters.UserItemsAdapter;
import com.kkroegeraraustech.farmingwithandroid.extras.Constants;
import com.kkroegeraraustech.farmingwithandroid.extras.UserItems_ExpandableList_Support.ExpandableListItems_Child;
import com.kkroegeraraustech.farmingwithandroid.extras.UserItems_ExpandableList_Support.ExpandableListItems_Parent;
import com.kkroegeraraustech.farmingwithandroid.R;
import com.kkroegeraraustech.farmingwithandroid.fragments.MainLaunchFragment;
import com.kkroegeraraustech.farmingwithandroid.views.SlidingTabLayout;

import java.util.ArrayList;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends FragmentActivity implements MaterialTabListener , Constants, TabHost.OnTabChangeListener, MainLaunchFragment.OnFragmentInteractionListener {
//public class MainActivity extends ActionBarActivity implements MaterialTabListener , Constants, TabHost.OnTabChangeListener {

    //Variables for SmartPadList
    //Setup for smartpad List
    public static final String TAB_MANAGE = "manage";
    public static final String TAB_BROWSE = "browse";

    private LayoutInflater inflater;
    private Resources res;

    private TabAdapter mTabAdapter;
    private Toolbar mToolBar;
    private ViewPager mViewPager;
    private SlidingTabLayout mTabs;
    private MaterialTabHost mTabHost;
    //private MyPagerAdapter mAdapter;
    private FragmentTransaction mTransAdapter;
    public static FragmentManager fragmentManager;

    UserItemsAdapter adapterUserItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            MainLaunchFragment firstFragment = new MainLaunchFragment();
            // Add the fragment to the 'fragment_container' FrameLayout
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container,firstFragment).commit();
        }


        //setupToolbar();
        //setupTabs();
    }

    private void setupToolbar() {
        mToolBar = (Toolbar) findViewById(R.id.app_bar);
        //setSupportActionBar(mToolBar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupTabs() {
        mTabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabAdapter = new TabAdapter(getSupportFragmentManager(),this);
        mViewPager.setAdapter(mTabAdapter);
        //when the page changes in the ViewPager, update the Tabs accordingly
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTabHost.setSelectedNavigationItem(position);

            }
        });
        //Add all the Tabs to the TabHost
        for (int i = 0; i < mTabAdapter.getCount(); i++) {
            mTabHost.addTab(
                    mTabHost.newTab()
                            .setIcon(mTabAdapter.getIcon(i))
                            .setTabListener(this));
        }
        /**
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        //make sure all tabs take the full horizontal screen space and divide them equally
        mTabs.setDistributeEvenly(true);
        mTabs.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        //color of the tab indicator
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        mTabs.setViewPager(mViewPager);
         */
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_activity_using_tab_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        //when a Tab is selected, update the ViewPager to reflect the changes
        mViewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {
    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //doUnbindService();
    }

    //SmartPad Related Functions

    private View createTabIndicator(String label) {
        View tabIndicator = inflater.inflate(R.layout.tabindicator, null);
        TextView tv = (TextView) tabIndicator.findViewById(R.id.label);
        tv.setText(label);
        return tabIndicator;
    }

    @Override
    public void onTabChanged(String tabId) {
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("Tag","MADE IT");
    }
    public void startActivityGuidance(View view){
        Log.d("Tag","This Fired");
    }
}
