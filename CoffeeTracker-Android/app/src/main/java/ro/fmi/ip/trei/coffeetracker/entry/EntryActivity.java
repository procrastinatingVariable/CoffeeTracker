package ro.fmi.ip.trei.coffeetracker.entry;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Arrays;
import java.util.List;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.databinding.ActivityEntryBinding;

public class EntryActivity extends Activity {

    private static final int RC_SIGN_IN = 1;

    private ActivityEntryBinding binding;

    private FirebaseAuth auth;

    private Toast currentToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_entry);

        auth = FirebaseAuth.getInstance();

        bindView();
    }

    private void bindView() {
        binding.signIn.setOnClickListener(v -> { doLogin(); });
    }

    private void getCurrentUser() {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            displayMessage("User not logged in");
            return;
        }

        String phoneNumber = user.getPhoneNumber();
        displayMessage("User logged in: " + (phoneNumber == null ? "null" : phoneNumber));
    }

    private void displayMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = toast;
        currentToast.show();
    }

    private void doLogout() {
        auth.signOut();
    }

    private void doLogin() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

}
