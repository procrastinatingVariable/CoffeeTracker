package ro.fmi.ip.trei.coffeetracker.addrecord;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ro.fmi.ip.trei.coffeetracker.addrecord.model.Beverage;

public class BeverageListPagerAdapter extends FragmentStatePagerAdapter {

    private BeverageListFragment[] fragmentList;
    private String[] fragmentTitles;

    public BeverageListPagerAdapter(FragmentManager fm) {
        super(fm);

        fragmentList = new BeverageListFragment[]{
                BeverageListFragment.newInstance(BeverageListFragment.Type.COFFEE),
                BeverageListFragment.newInstance(BeverageListFragment.Type.TEA),
                BeverageListFragment.newInstance(BeverageListFragment.Type.SOFT_DRINK)
        };
        fragmentTitles = new String[]{
                "Coffee",
                "Tea",
                "Soft Drink"
        };
    }


    @Override
    public Fragment getItem(int i) {
        return fragmentList[i];
    }

    @Override
    public int getCount() {
        return fragmentList.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles[position];
    }

}
