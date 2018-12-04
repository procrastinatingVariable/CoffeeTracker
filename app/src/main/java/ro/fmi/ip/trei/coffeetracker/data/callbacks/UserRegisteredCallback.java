package ro.fmi.ip.trei.coffeetracker.data.callbacks;


import ro.fmi.ip.trei.coffeetracker.data.model.User;

public interface UserRegisteredCallback {
    void userExists(User user);
    void userNonexistent();
}
