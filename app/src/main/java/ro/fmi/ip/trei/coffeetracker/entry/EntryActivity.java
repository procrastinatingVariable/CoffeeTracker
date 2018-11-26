package ro.fmi.ip.trei.coffeetracker.entry;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.common.BaseActivity;
import ro.fmi.ip.trei.coffeetracker.databinding.ActivityEntryBinding;
import ro.fmi.ip.trei.coffeetracker.entry.signin.OtpFragment;
import ro.fmi.ip.trei.coffeetracker.entry.signin.PhoneInsertFragment;
import ro.fmi.ip.trei.coffeetracker.entry.signup.FillProfileFragment;
import ro.fmi.ip.trei.coffeetracker.main.MainActivity;

public class EntryActivity extends BaseActivity {

    private static final String DEBUG_TAG = EntryActivity.class.getSimpleName();



    private ActivityEntryBinding binding;
    private EntryActivityViewModel viewModel;
    private FragmentManager fragmentManager;

    // === Lifecycle ===

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel.goToPhoneInsertSubscreen();
    }


    // ^^^ Lifecycle ^^^

    // === BaseActivity ===

    @Override
    protected void bindFields() {
        viewModel = ViewModelProviders.of(this).get(EntryActivityViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_entry);
        binding.setViewModel(viewModel);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void subscribeToViewModel() {
        viewModel.getCurrentScreenEvent().observe(this, this::moveToScreen);
        viewModel.getCurrentSubscreenEvent().observe(this, this::moveToSubscreen);
        viewModel.getErrorTextEvent().observe(this, this::displayError);
    }

    // ^^^ BaseActivity ^^^

    private void displayError(int errorType) {
        switch (errorType) {
            case EntryActivityViewModel.ERROR_INVALID_PHONE:
                displayToastMessage(getString(R.string.error_invalid_phone));
                break;

            case EntryActivityViewModel.ERROR_OTP:
                displayToastMessage(getString(R.string.error_invalid_otp));
                break;

            case EntryActivityViewModel.ERROR_TIMEOUT:
                displayToastMessage(getString(R.string.error_timeout));
                break;

            default:
                displayToastMessage(getString(R.string.error_default));
        }
    }

    private void moveToScreen(int screenId) {
        Intent intent = new Intent();
        switch (screenId) {
            case EntryActivityViewModel.SCREEN_MAIN:
                intent.setClass(this, MainActivity.class);
                break;
            default:
                Log.d(DEBUG_TAG, "Couldn't recognize screen id: " + Integer.toString(screenId));
        }

        if (intent.getComponent() != null) {
            startActivity(intent);
            finish();
        }

    }

    private void moveToSubscreen(int screenId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        switch (screenId) {
            case EntryActivityViewModel.STEP_PHONEINSERT:
                fragment = new PhoneInsertFragment();
                break;

            case EntryActivityViewModel.STEP_OTP:
                fragment = new OtpFragment();
                break;

            case EntryActivityViewModel.STEP_PROFILE:
                fragment = new FillProfileFragment();
                break;

            default:
                Log.d(DEBUG_TAG, "Couldn't recognize screen id: " + Integer.toString(screenId));
        }

        if (fragment != null) {
            fragmentTransaction.replace(R.id.fragment_container, fragment);

            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() <= 1) {
            finish();
        }
    }
}
