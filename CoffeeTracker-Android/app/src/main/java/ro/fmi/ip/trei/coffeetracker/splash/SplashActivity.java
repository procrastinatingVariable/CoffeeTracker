package ro.fmi.ip.trei.coffeetracker.splash;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.common.BaseActivity;
import ro.fmi.ip.trei.coffeetracker.entry.EntryActivity;
import ro.fmi.ip.trei.coffeetracker.main.MainActivity;

public class SplashActivity extends BaseActivity {

    private static final String DEBUG_TAG = SplashActivity.class.getSimpleName();



    private SplashActivityViewModel viewModel;

    // === Lifecycle ===

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        coolDelayedAdvance();
    }

    // ^^^ Lifecycle ^^^

    // === BaseActivity ===

    @Override
    protected void bindFields() {
        viewModel = ViewModelProviders.of(this).get(SplashActivityViewModel.class);
    }

    @Override
    protected void subscribeToViewModel() {
        viewModel.nextScreenEvent.observe(this, integer -> {
            int screenId = 0;
            if (integer != null) {
                screenId = integer;
            }

            moveToScreen(screenId);
        });
    }

    // ^^^ BaseActivity ^^^

    private void coolDelayedAdvance() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            viewModel.checkLoginAndAdvance();

        }, 2000);
    }

    private void moveToScreen(int screenId) {
        Intent intent = new Intent();
        switch(screenId) {
            case SplashActivityViewModel.SCREEN_ID_MAIN:
                intent.setClass(this, MainActivity.class);
                break;

            case SplashActivityViewModel.SCREEN_ID_ENTRY:
                intent.setClass(this, EntryActivity.class);
                break;

            default:
                Log.w(DEBUG_TAG, "Invalid screenId " + Integer.toString(screenId));
        }
        try {
            startActivity(intent);
            finish();
        } catch (ActivityNotFoundException e) {
            Log.w(DEBUG_TAG, "Coudln't start Activity: " + intent.getComponent().getClassName());
        }
    }

}
