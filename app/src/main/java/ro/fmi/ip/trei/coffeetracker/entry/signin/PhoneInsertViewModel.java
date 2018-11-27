package ro.fmi.ip.trei.coffeetracker.entry.signin;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ro.fmi.ip.trei.coffeetracker.common.BaseViewModel;
import ro.fmi.ip.trei.coffeetracker.entry.EntryViewModel;

public class PhoneInsertViewModel extends BaseViewModel {

    public static final String DEBUG_TAG = PhoneInsertViewModel.class.getSimpleName();



    private PhoneAuthProvider phoneAuthProvider;

    private EntryViewModel flowViewModel;
    private PhoneNumberValidator phoneNumberValidator;

    private String phoneNumber;



    public PhoneInsertViewModel(EntryViewModel flowViewModel) {
        this.flowViewModel = flowViewModel;
        phoneAuthProvider = PhoneAuthProvider.getInstance();
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneNumberValidator(PhoneNumberValidator validator) {
        this.phoneNumberValidator = validator;
    }

    public void validatePhoneNumber() {
        if (phoneNumberValidator != null) {
            if (phoneNumberValidator.validate(phoneNumber)) {
                sendOtpMessage();
                flowViewModel.getLoginStateBuilder().phoneNumber(phoneNumber);
            } else {
                flowViewModel.setError(EntryViewModel.ERROR_INVALID_PHONE);
            }
        }
    }

    private void sendOtpMessage() {
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
                        flowViewModel.setError(EntryViewModel.ERROR_TIMEOUT);
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        flowViewModel.getLoginStateBuilder().otpVerificationId(verificationId);
                        flowViewModel.goToOtpSubscreen();
                    }
                }
        );
    }

    public interface PhoneNumberValidator {
        boolean validate(String phoneNumber);
    }

}
