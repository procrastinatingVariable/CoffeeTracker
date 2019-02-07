package ro.fmi.ip.trei.coffeetracker.data.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ro.fmi.ip.trei.coffeetracker.data.callbacks.GetRecordsCallback;
import ro.fmi.ip.trei.coffeetracker.data.callbacks.GetScoresCallback;
import ro.fmi.ip.trei.coffeetracker.data.model.ScoreEntity;

public class FirebaseScoreDatabaseHelper {

    private static volatile FirebaseScoreDatabaseHelper instance = null;


    private DatabaseReference databaseReference;


    private FirebaseScoreDatabaseHelper() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("scores");
    }


    public static FirebaseScoreDatabaseHelper getInstance() {
        if (instance == null) {
            synchronized (FirebaseScoreDatabaseHelper.class) {
                if (instance == null) {
                    instance = new FirebaseScoreDatabaseHelper();
                }
            }
        }

        return instance;
    }

    public void getScores(String phoneNumber, GetScoresCallback callback) {
        DatabaseReference user = databaseReference.child(phoneNumber);
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> records = dataSnapshot.getChildren();
                List<ScoreEntity> scoreEntity = new ArrayList<>();
                for (DataSnapshot record : records) {
                    ScoreEntity r = record.getValue(ScoreEntity.class);
                    scoreEntity.add(r);
                }

                callback.onResult(scoreEntity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addScore(String phoneNumber, ScoreEntity scoreEntity) {
        DatabaseReference referenceForPhoneNumber = databaseReference.child(phoneNumber);
        DatabaseReference referenceForEntry = referenceForPhoneNumber.push();
        referenceForEntry.setValue(scoreEntity);
    }

}
