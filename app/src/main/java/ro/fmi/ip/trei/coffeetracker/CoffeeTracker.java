package ro.fmi.ip.trei.coffeetracker;

import android.app.Application;

import com.google.firebase.ml.custom.FirebaseModelManager;
import com.google.firebase.ml.custom.model.FirebaseCloudModelSource;
import com.google.firebase.ml.custom.model.FirebaseModelDownloadConditions;

public class CoffeeTracker extends Application {

    public static final String FIREBASE_ML_MODEL = "coffee-predictor.tflite";

    // vvvvvv LIFECYCLE vvvvvv

    @Override
    public void onCreate() {
        super.onCreate();

        configureFirebaseML();
    }

    private void configureFirebaseML() {
        FirebaseModelDownloadConditions.Builder conditionBuilder =
                new FirebaseModelDownloadConditions.Builder().requireWifi();
        FirebaseModelDownloadConditions conditions = conditionBuilder.build();

        FirebaseCloudModelSource cloudSource = new FirebaseCloudModelSource.Builder(FIREBASE_ML_MODEL)
                .enableModelUpdates(true)
                .setInitialDownloadConditions(conditions)
                .setUpdatesDownloadConditions(conditions)
                .build();
        FirebaseModelManager.getInstance().registerCloudModelSource(cloudSource);
    }

    // ^^^^^^ LIFECYCLE ^^^^^^


}
