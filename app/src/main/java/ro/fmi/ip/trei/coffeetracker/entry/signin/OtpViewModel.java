package ro.fmi.ip.trei.coffeetracker.entry.signin;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.PhoneAuthProvider;

import ro.fmi.ip.trei.coffeetracker.entry.EntryViewModel;
import ro.fmi.ip.trei.coffeetracker.entry.LoginState;

public class OtpViewModel extends ViewModel {

    public static final String DEBUG_TAG = OtpViewModel.class.getSimpleName();

    public final MutableLiveData<String> otp = new MutableLiveData<>();


    public EntryViewModel flowViewModel;

    public OtpViewModel(EntryViewModel viewModel) {
        this.flowViewModel = viewModel;
    }

    public void signIn() {
        String otpString = otp.getValue();
        if (otpString == null || otpString.isEmpty()) {
            flowViewModel.setError(EntryViewModel.ERROR_OTP);
            return;
        }

        LoginState state = flowViewModel.getLoginState();

        flowViewModel.signIn(PhoneAuthProvider.getCredential(state.getOtpVerificationId(), otpString));
    }
}
