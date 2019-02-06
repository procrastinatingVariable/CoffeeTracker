package ro.fmi.ip.trei.coffeetracker.data.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ro.fmi.ip.trei.coffeetracker.data.callbacks.GetBeverageCallback;
import ro.fmi.ip.trei.coffeetracker.data.model.BeverageEntity;

public class FirebaseBeverageHelper {

    private static volatile FirebaseBeverageHelper instance = null;


    private DatabaseReference databaseReference;


    private FirebaseBeverageHelper() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("drinks");
    }


    public static FirebaseBeverageHelper getInstance() {
        if (instance == null) {
            synchronized (FirebaseRecordDatabaseHelper.class) {
                if (instance == null) {
                    instance = new FirebaseBeverageHelper();
                }
            }
        }

        return instance;
    }


    public void getCoffeeRecords(GetBeverageCallback callback) {
        getBeverage("coffee", callback);
    }

    public void getTeaRecords(GetBeverageCallback callback) {
        getBeverage("tea", callback);
    }

    public void getSoftDrinkRecords(GetBeverageCallback callback) {
        getBeverage("energyDrink", callback);
    }

    public void getBeverage(String type, GetBeverageCallback callback) {
        DatabaseReference coffeeEntries = databaseReference.child(type);
        coffeeEntries.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> beverages = dataSnapshot.getChildren();
                List<BeverageEntity> beverageList = new ArrayList<>();
                for (DataSnapshot beverage : beverages) {
                    BeverageEntity r = beverage.getValue(BeverageEntity.class);
                    beverageList.add(r);
                }

                callback.onResult(beverageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
