package ro.fmi.ip.trei.coffeetracker.entry.signin;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import ro.fmi.ip.trei.coffeetracker.entry.EntryActivity;
import ro.fmi.ip.trei.coffeetracker.entry.EntryActivityViewModel;
import ro.fmi.ip.trei.coffeetracker.entry.LoginState;

public class OtpFragmentViewModel extends ViewModel {

    public static final String DEBUG_TAG = OtpFragmentViewModel.class.getSimpleName();

    public final MutableLiveData<String> otp = new MutableLiveData<>();


    public EntryActivityViewModel flowViewModel;

    public OtpFragmentViewModel(EntryActivityViewModel viewModel) {
        this.flowViewModel = viewModel;
    }

    public void signIn() {
        String otpString = otp.getValue();
        if (otpString == null || otpString.isEmpty()) {
            flowViewModel.setError(EntryActivityViewModel.ERROR_OTP);
            return;
        }

        LoginState state = flowViewModel.getLoginState();

        flowViewModel.signIn(PhoneAuthProvider.getCredential(state.getOtpVerificationId(), otpString));
    }
}
