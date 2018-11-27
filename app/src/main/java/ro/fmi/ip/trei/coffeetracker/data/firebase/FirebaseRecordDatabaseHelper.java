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
import ro.fmi.ip.trei.coffeetracker.data.model.RecordEntity;

public class FirebaseRecordDatabaseHelper {

    private static volatile FirebaseRecordDatabaseHelper instance = null;


    private DatabaseReference databaseReference;


    private FirebaseRecordDatabaseHelper() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("records");
    }


    public static FirebaseRecordDatabaseHelper getInstance() {
        if (instance == null) {
            synchronized (FirebaseRecordDatabaseHelper.class) {
                if (instance == null) {
                    instance = new FirebaseRecordDatabaseHelper();
                }
            }
        }

        return instance;
    }


    public void getRecords(String phoneNumber, GetRecordsCallback callback) {
        DatabaseReference user = databaseReference.child(phoneNumber);
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> records = dataSnapshot.getChildren();
                List<RecordEntity> recordList = new ArrayList<>();
                for (DataSnapshot record : records) {
                    RecordEntity r = record.getValue(RecordEntity.class);
                    recordList.add(r);
                }

                callback.onResult(recordList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
