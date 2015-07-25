package com.kkroegeraraustech.farmingwithandroid.activities;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;


import com.kkroegeraraustech.farmingwithandroid.R;
import com.kkroegeraraustech.farmingwithandroid.adapters.TabAdapter;
import com.kkroegeraraustech.farmingwithandroid.extras.Constants;
import com.kkroegeraraustech.farmingwithandroid.fragments.CreateMapBoundayFragment;
import com.kkroegeraraustech.farmingwithandroid.fragments.MainLaunchFragment;
import com.kkroegeraraustech.farmingwithandroid.fragments.NewFieldFragment;


import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by Edwin on 15/02/2015.
 */
public class MainActivity extends FragmentActivity implements MaterialTabListener , Constants, TabHost.OnTabChangeListener, MainLaunchFragment.OnFragmentInteractionListener {
//public class MainActivity extends AppCompatActivity implements MaterialTabListener, NewFieldFragment.OnFragmentInteractionListener, CreateMapBoundayFragment.OnFragmentInteractionListener{
    private Toolbar mToolbar;
    private MaterialTabHost mTabHost;
    private ViewPager mViewPager;

    @Override
    public void onTabChanged(String tabId) {

    }

    private TabAdapter mAdapter;


    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_tabs);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);


        mAdapter = new TabAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTabHost.setSelectedNavigationItem(position);

            }
        });

        for (int i = 0; i < mAdapter.getCount(); i++) {
            MaterialTab materialTab = mTabHost.newTab();
            materialTab.setText(mAdapter.getPageTitle(i));
            materialTab.setTabListener(this);
            mTabHost.addTab(materialTab);
        }
    }


*/


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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dynamic_tabs, menu);
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
        if (R.id.add_tabs == id) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        mViewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}