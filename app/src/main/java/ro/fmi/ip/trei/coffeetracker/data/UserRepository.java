package ro.fmi.ip.trei.coffeetracker.data;

import ro.fmi.ip.trei.coffeetracker.data.callbacks.UserRegisteredCallback;
import ro.fmi.ip.trei.coffeetracker.data.firebase.FirebaseDatabaseHelper;
import ro.fmi.ip.trei.coffeetracker.data.model.UserEntity;

public class UserRepository {

    private static volatile UserRepository instance = null;

    private FirebaseDatabaseHelper fbDbHelper;


    private UserRepository() {
        this.fbDbHelper = new FirebaseDatabaseHelper(FirebaseDatabaseHelper.DATABASE_USERS);
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }

        return instance;
    }

    public void userRegistered(String phoneNumber, UserRegisteredCallback callback) {
        fbDbHelper.userRegistered(phoneNumber, callback);
    }

    public void registerUser(UserEntity user) {
        fbDbHelper.registerUser(user);
    }

}
