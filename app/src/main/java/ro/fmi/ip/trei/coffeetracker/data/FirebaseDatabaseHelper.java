package ro.fmi.ip.trei.coffeetracker.data;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ro.fmi.ip.trei.coffeetracker.data.callbacks.UserRegisteredCallback;
import ro.fmi.ip.trei.coffeetracker.data.model.FirebaseUserEntity;
import ro.fmi.ip.trei.coffeetracker.data.model.UserEntity;
import ro.fmi.ip.trei.coffeetracker.util.ModelMapper;

public class FirebaseDatabaseHelper {

    public static final int DATABASE_USERS = 1;

    private DatabaseReference databaseReference;

    public FirebaseDatabaseHelper(int database) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        switch(database) {
            case DATABASE_USERS:
                databaseReference = db.getReference("users");
                break;

            default:
                databaseReference = db.getReference();
        }
    }

    public void userRegistered(String phoneNumber, UserRegisteredCallback callback) {
        databaseReference.child(phoneNumber)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            FirebaseUserEntity user = dataSnapshot.getValue(FirebaseUserEntity.class);
                            if (user != null) {
                                UserEntity modelUser = ModelMapper.map(user);
                                modelUser.setPhoneNumber(phoneNumber);
                                callback.userExists(modelUser);
                            } else {
                                callback.userNonexistent();
                            }
                        } else {
                            callback.userNonexistent();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void registerUser(UserEntity user) {
        databaseReference.child(user.getPhoneNumber())
                .setValue(ModelMapper.map(user));
    }

}
