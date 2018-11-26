package ro.fmi.ip.trei.coffeetracker.data.callbacks;


import ro.fmi.ip.trei.coffeetracker.data.model.UserEntity;

public interface UserRegisteredCallback {
    void userExists(UserEntity user);
    void userNonexistent();
}
