package ro.fmi.ip.trei.coffeetracker.util;

import ro.fmi.ip.trei.coffeetracker.data.model.FirebaseUser;
import ro.fmi.ip.trei.coffeetracker.data.model.User;
import ro.fmi.ip.trei.coffeetracker.entry.model.Formats;

public class ModelMapper {

    public static User map(ro.fmi.ip.trei.coffeetracker.entry.model.User user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPhoneNumber(user.getPhoneNumber());
        String birthdateString = Formats.getDateFormat().format(user.getBirthDate());
        newUser.setBirthDate(birthdateString);

        return newUser;
    }

    public static User map(FirebaseUser user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setBirthDate(user.getBirthDate());
        return newUser;
    }

    public static FirebaseUser map(User user) {
        FirebaseUser newUser = new FirebaseUser();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setBirthDate(user.getBirthDate());
        return newUser;
    }

}


