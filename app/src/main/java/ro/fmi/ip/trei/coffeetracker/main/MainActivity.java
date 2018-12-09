package ro.fmi.ip.trei.coffeetracker.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.common.BaseActivity;
import ro.fmi.ip.trei.coffeetracker.databinding.ActivityMainBinding;
import ro.fmi.ip.trei.coffeetracker.main.dashboard.DashboardFragment;
import ro.fmi.ip.trei.coffeetracker.main.profile.ProfileFragment;
import ro.fmi.ip.trei.coffeetracker.main.records.RecordsFragment;
import ro.fmi.ip.trei.coffeetracker.pvt.PvtActivity;


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.item_pvt:
                testPvt();
                break;

            default:
                Log.w(DEBUG_TAG, "Couldn't recognize option item id");
                return false;
        }

        return true;
    }

    private void testPvt() {
        final String CHANNEL_ID = "channelid";
        final String CHANNEL_NAME = "whatever";

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager.getNotificationChannel("channelId") == null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        //Get an instance of NotificationManager//
        Intent notifyIntent = new Intent(this, PvtActivity.class);
        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_stat_thumb_up)
                        .setContentTitle("Test PVT")
                        .setContentText("Fa urmatorul test.")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(notifyPendingIntent);


        // Gets an instance of the NotificationManager service//

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(001, mBuilder.build());
    }

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
