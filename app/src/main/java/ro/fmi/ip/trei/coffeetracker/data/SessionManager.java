package ro.fmi.ip.trei.coffeetracker.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ro.fmi.ip.trei.coffeetracker.util.ModelMapper;

public class SessionManager {

    private static volatile SessionManager instance;


    private FirebaseAuth auth;

    private SessionManager() {
        auth = FirebaseAuth.getInstance();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager();
                }
            }
        }

        return instance;
    }

    public FirebaseUser getUser() {
        return auth.getCurrentUser();
    }

}
