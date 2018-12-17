package ro.fmi.ip.trei.coffeetracker.entry;

import android.arch.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;

import ro.fmi.ip.trei.coffeetracker.common.BaseViewModel;
import ro.fmi.ip.trei.coffeetracker.data.UserRepository;
import ro.fmi.ip.trei.coffeetracker.data.callbacks.UserRegisteredCallback;
import ro.fmi.ip.trei.coffeetracker.data.model.UserEntity;
import ro.fmi.ip.trei.coffeetracker.entry.model.User;
import ro.fmi.ip.trei.coffeetracker.util.ModelMapper;
import ro.fmi.ip.trei.coffeetracker.util.SingleLiveEvent;

public class EntryViewModel extends BaseViewModel {

    public static final int SCREEN_MAIN = 1;
    public static final int STEP_PHONEINSERT = 2;
    public static final int STEP_OTP = 3;
    public static final int STEP_PROFILE = 4;

    public static final int ERROR_INVALID_PHONE = 40;
    public static final int ERROR_INVALID_CREDENTIALS = 41;
    public static final int ERROR_REGISTER = 42;
    public static final int ERROR_OTP = 43;
    public static final int ERROR_TIMEOUT = 44;
    public static final int ERROR_PLAY_SERVICES = 45;
    public static final int ERROR_FIREBASE_AUTH = 46;

    private SingleLiveEvent<Integer> currentSubscreenEvent = new SingleLiveEvent<>();

    private SingleLiveEvent<Integer> errorTextEvent = new SingleLiveEvent<>();

    private LoginState.Builder loginStateBuilder = new LoginState.Builder();

    private FirebaseAuth auth;

    private UserRepository repository;



    public EntryViewModel() {
        auth = FirebaseAuth.getInstance();
        repository = UserRepository.getInstance();
    }



    public void signIn(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            setError(ERROR_OTP);
                        } else {
                            setError(ERROR_INVALID_CREDENTIALS);
                        }
                    } else {
                        userIsNew(loginStateBuilder.build().getPhoneNumber(), new UserRegisteredCallback() {
                            @Override
                            public void userExists(UserEntity user) {
                                setCurrentScreen(SCREEN_MAIN);
                            }

                            @Override
                            public void userNonexistent() {
                                goToFillProNfileSubscreen();
                            }
                        });
                    }
                });
    }

    public LoginState.Builder getLoginStateBuilder() {
        return loginStateBuilder;
    }

    public LoginState getLoginState() {
        return loginStateBuilder.build();
    }

    public void setCurrentSubscreen(int screenId) {
        currentSubscreenEvent.postValue(screenId);
    }

    public LiveData<Integer> getCurrentSubscreenEvent() {
        return currentSubscreenEvent;
    }

    public LiveData<Integer> getErrorTextEvent() {
        return errorTextEvent;
    }

    public void goToMainScreen() {
        setCurrentScreen(SCREEN_MAIN);
    }

    public void goToPhoneInsertSubscreen() {
        setCurrentSubscreen(STEP_PHONEINSERT);
    }

    public void goToOtpSubscreen() {
        setCurrentSubscreen(STEP_OTP);
    }

    public void goToFillProNfileSubscreen() {
        setCurrentSubscreen(STEP_PROFILE);
    }

    private void userIsNew(String phoneNumber, UserRegisteredCallback callback) {
        repository.userRegistered(phoneNumber, callback);
    }

    public void registerUser() {
        LoginState loginState = getLoginState();
        User user = new User(
                loginState.getPhoneNumber(),
                loginState.getFirstName(),
                loginState.getLastName(),
                loginState.getBirthDate());

        repository.registerUser(ModelMapper.map(user));
    }

    public void setError(int errorType) {
        errorTextEvent.postValue(errorType);
    }



    public interface SignInCompleteCallback {
        void call();
    }

    public interface RegisterUserCallback {
        void success();
        void error();
    }
}
