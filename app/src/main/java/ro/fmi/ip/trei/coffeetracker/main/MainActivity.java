package ro.fmi.ip.trei.coffeetracker.main;

import android.os.Bundle;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.common.BaseActivity;
import ro.fmi.ip.trei.coffeetracker.databinding.ActivityMainBinding;
import ro.fmi.ip.trei.coffeetracker.main.dashboard.DashboardFragment;
import ro.fmi.ip.trei.coffeetracker.main.profile.ProfileFragment;
import ro.fmi.ip.trei.coffeetracker.main.records.RecordsFragment;


public class MainActivity extends BaseActivity {

    private static final String DEBUG_TAG = MainActivity.class.getSimpleName();



    private ActivityMainBinding binding;
    private FragmentManager fragmentManager;



    // === Lifecycle ===

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }


    // ^^^ Lifecycle ^^^

    // === BaseActivity ===

    @Override
    protected void subscribeToViewModel() {

    }

    @Override
    protected void bindFields() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
    }

    // ^^^ BaseActivity ^^^

    private void initView() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this::onBottomNavigationItemSelected);
        Fragment fragment = new DashboardFragment();
        goToSubscreen(fragment);
    }

    private boolean onBottomNavigationItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        Fragment fragment;
        switch(itemId) {
            case R.id.tab_dashboard:
                fragment = new DashboardFragment();
                break;

            case R.id.tab_profile:
                fragment = new ProfileFragment();
                break;

            case R.id.tab_records:
                fragment = new RecordsFragment();
                break;

            default:
                Log.w(DEBUG_TAG, "Item id not recognized: " + Integer.toString(itemId));
                return false;

        }
        goToSubscreen(fragment);
        return true;
    }

    private void goToSubscreen(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fragmentManager.getBackStackEntryCount() == 1) {
            finish();
        }
    }
}
