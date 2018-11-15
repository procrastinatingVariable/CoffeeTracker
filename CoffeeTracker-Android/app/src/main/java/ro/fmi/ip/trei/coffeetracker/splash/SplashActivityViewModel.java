package ro.fmi.ip.trei.coffeetracker.splash;

import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import ro.fmi.ip.trei.coffeetracker.util.SingleLiveEvent;

public class SplashActivityViewModel extends ViewModel {

    public static final int SCREEN_ID_MAIN = 10;
    public static final int SCREEN_ID_ENTRY = 11;



    public final SingleLiveEvent<Integer> nextScreenEvent = new SingleLiveEvent<>();



    private FirebaseAuth auth;



    public SplashActivityViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    public void checkLoginAndAdvance() {
        if (userIsLoggedIn()) {
            nextScreenEvent.setValue(SCREEN_ID_MAIN);
        } else {
            nextScreenEvent.setValue(SCREEN_ID_ENTRY);
        }
    }

    private boolean userIsLoggedIn() {
        return !(auth.getCurrentUser() == null);
    }
}
