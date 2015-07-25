package com.kkroegeraraustech.farmingwithandroid.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kkroegeraraustech.farmingwithandroid.extras.Constants;
import com.kkroegeraraustech.farmingwithandroid.fragments.DummyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ken Heron Systems on 7/22/2015.
 */
public class SoilsTabAdapter extends FragmentStatePagerAdapter implements Constants {

    private CharSequence[] Titles;// This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    public SoilsTabAdapter(FragmentManager fm){
        super(fm);
        List<String> listItems = new ArrayList<String>();
        listItems.add("Item1");
        listItems.add("Item2");
        listItems.add("Item3");

        Titles = listItems.toArray(new CharSequence[listItems.size()]);
        NumbOfTabs = listItems.size();

    }
    // Build a Constructor and assign the passed Values to appropriate values in the class
    public SoilsTabAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            DummyFragment tab1 = new DummyFragment();
            return tab1;
        }
        else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            DummyFragment tab2 = new DummyFragment();
            return tab2;
        }

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
