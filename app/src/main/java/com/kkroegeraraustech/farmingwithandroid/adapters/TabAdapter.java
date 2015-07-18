package com.kkroegeraraustech.farmingwithandroid.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kkroegeraraustech.farmingwithandroid.R;
import com.kkroegeraraustech.farmingwithandroid.extras.Constants;
import com.kkroegeraraustech.farmingwithandroid.fragments.DummyFragment;


/**
 * Created by Ken Heron Systems on 6/2/2015.
 */
public class TabAdapter extends FragmentPagerAdapter implements Constants {

    private final Context mContext;
    int icons[] = {R.drawable.ic_action_search_orange,
            R.drawable.ic_action_trending_orange,
            R.drawable.ic_action_upcoming_orange};

    FragmentManager fragmentManager;

    public TabAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        fragmentManager = fm;
    }

    public Fragment getItem(int num) {
        Fragment fragment = null;
//            L.m("getItem called for " + num);
        switch (num) {
            case TAB_NOTES:
                fragment = DummyFragment.getInstance(num);
                //fragment = FragmentSearch.newInstance("", "");
                break;
            case TAB_GUIDANCE:
                fragment = DummyFragment.getInstance(num);
                break;
            case TAB_IMAGES:
                fragment = DummyFragment.getInstance(num);
                break;
        }
        return fragment;

    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getStringArray(R.array.tabs)[position];
        //return context.getStringArray(R.array.tabs)[position];
    }

    public Drawable getIcon(int position) {
        return mContext.getResources().getDrawable(icons[position]);
        //return getResources().getDrawable(icons[position]);
    }
}
