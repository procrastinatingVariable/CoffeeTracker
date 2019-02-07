package ro.fmi.ip.trei.coffeetracker.splash;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.common.BaseActivity;
import ro.fmi.ip.trei.coffeetracker.entry.EntryActivity;
import ro.fmi.ip.trei.coffeetracker.entry.LoginActivity;
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
        viewModel.getCurrentScreenEvent().observe(this, this::moveToScreen);
    }

    // ^^^ BaseActivity ^^^

    private void moveToScreen(Integer screenId) {
        if (screenId == null) {
            return;
        }

        Intent intent = new Intent();
        switch(screenId) {
            case SplashActivityViewModel.SCREEN_ENTRY:
                intent.setClass(this, LoginActivity.class);
                break;

            case SplashActivityViewModel.SCREEN_MAIN:
                intent.setClass(this, MainActivity.class);
                break;

            default:
                Log.w(DEBUG_TAG, "Can't recognize screen id: " + Integer.toString(screenId));
        }

        if (intent.getComponent() != null) {
            startActivity(intent);
        }
    }

    private void coolDelayedAdvance() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            viewModel.checkLoginAndAdvance();
            SplashActivity.this.finish();
            }, 2000
        );
    }

}
