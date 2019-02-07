package ro.fmi.ip.trei.coffeetracker.entry;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.common.BaseActivity;
import ro.fmi.ip.trei.coffeetracker.data.callbacks.UserRegisteredCallback;
import ro.fmi.ip.trei.coffeetracker.data.model.UserEntity;
import ro.fmi.ip.trei.coffeetracker.databinding.ActivityLoginBinding;
import ro.fmi.ip.trei.coffeetracker.main.MainActivity;

public class LoginActivity extends BaseActivity {

    private static final String DEFAULT_COUNTY_CODE = "ro";


    private ActivityLoginBinding binding;

    private PhoneAuthProvider phoneAuthProvider;
    private FirebaseAuth firebaseAuth;

    private String smsVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        phoneAuthProvider = PhoneAuthProvider.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        initView();

        bindButtons();
    }

    private void bindButtons() {
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = binding.countryCodePicker.getFullNumberWithPlus();
                validatePhoneNumberAndSendOtp(phoneNumber);
            }
        });
    }


    private void validatePhoneNumberAndSendOtp(String phone) {
        if (binding.countryCodePicker.isValidFullNumber()) {
            sendOtpMessage(phone);
            displayOtpField();
            binding.nextButton.setOnClickListener(v -> validateCodeAndLogIn());
        } else {
            Toast.makeText(this, R.string.error_invalid_phone, Toast.LENGTH_SHORT).show();
        }
    }

    private void sendOtpMessage(String phoneNumber) {
        phoneAuthProvider.verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                Executors.newSingleThreadExecutor(),
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(LoginActivity.this, R.string.error_invalid_phone, Toast.LENGTH_SHORT).show();
                        } else if (e instanceof FirebaseAuthException) {
                            Toast.makeText(LoginActivity.this, R.string.error_app_unauthorized, Toast.LENGTH_SHORT).show();
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            Toast.makeText(LoginActivity.this, R.string.error_timeout, Toast.LENGTH_SHORT).show();
                        } else if (e instanceof FirebaseApiNotAvailableException) {
                            Toast.makeText(LoginActivity.this, R.string.error_api_missing, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.error_default, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        smsVerificationId = verificationId;
                    }
                }
        );
    }

    private void displayOtpField() {
        binding.smsCode.setVisibility(View.VISIBLE);
    }

    private void validateCodeAndLogIn() {
        String otpString = binding.otpField.getText().toString();
        if (otpString == null || otpString.isEmpty()) {
            Toast.makeText(this, R.string.error_invalid_otp, Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithCredential(PhoneAuthProvider.getCredential(smsVerificationId, otpString))
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(this, R.string.error_invalid_otp, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.error_invalid_phone, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        goToMainScreen();
                    }
                });
    }

    private void initView() {
        binding.countryCodePicker.setCountryForNameCode(DEFAULT_COUNTY_CODE);
        binding.countryCodePicker.registerCarrierNumberEditText(binding.phoneField);
    }

    private void goToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void subscribeToViewModel() {

    }

    @Override
    protected void bindFields() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }
}
