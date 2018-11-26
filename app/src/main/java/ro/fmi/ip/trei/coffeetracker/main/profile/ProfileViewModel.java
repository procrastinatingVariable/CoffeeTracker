package ro.fmi.ip.trei.coffeetracker.main.profile;

import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import ro.fmi.ip.trei.coffeetracker.common.BaseActivity;
import ro.fmi.ip.trei.coffeetracker.util.SingleLiveEvent;

public class ProfileViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public final SingleLiveEvent<Void> signOutEvent = new SingleLiveEvent<>();

    private FirebaseAuth auth;

    public ProfileViewModel() {
        this.auth = FirebaseAuth.getInstance();
    }

    public void signOut() {
        auth.signOut();
        signOutEvent.call();
    }
}
