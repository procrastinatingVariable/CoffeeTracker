package ro.fmi.ip.trei.coffeetracker.splash;

import com.google.firebase.auth.FirebaseAuth;

import ro.fmi.ip.trei.coffeetracker.common.BaseViewModel;

public class SplashActivityViewModel extends BaseViewModel {

    public static final int SCREEN_MAIN = 1;
    public static final int SCREEN_ENTRY = 2;



    private FirebaseAuth auth;



    public SplashActivityViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    public void checkLoginAndAdvance() {
        if (userIsLoggedIn()) {
            setCurrentScreen(SCREEN_MAIN);
        } else {
            setCurrentScreen(SCREEN_ENTRY);
        }
    }

    private boolean userIsLoggedIn() {
        return !(auth.getCurrentUser() == null);
    }
}
