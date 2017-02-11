package brianmccabe.coffeenow.ui;

/**
 * Created by brian on 04/02/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import brianmccabe.coffeenow.models.Results;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private Results[] results;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, Results[] results) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.results = results;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("name", results);
        switch (position) {
            case 0:

                CafeListFragment cafeListFragment = new CafeListFragment();
                cafeListFragment.setArguments(bundle);
                return cafeListFragment;
            case 1:
                CafeMapFragment cafeMapFragment = new CafeMapFragment();
                cafeMapFragment.setArguments(bundle);
                return cafeMapFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}