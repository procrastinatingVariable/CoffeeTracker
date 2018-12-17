package ro.fmi.ip.trei.coffeetracker.addrecord;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Random;

import androidx.work.Worker;
import androidx.work.WorkerParameters;
import ro.fmi.ip.trei.coffeetracker.PVTTest;
import ro.fmi.ip.trei.coffeetracker.R;

public class NotifyWorker extends Worker {

    private final String CHANNEL_ID = "ID";

    public NotifyWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Worker.Result doWork() {
        // Method to trigger an instant notification
        triggerNotification();

        return Worker.Result.success();
        // (Returning RETRY tells WorkManager to try this task again
        // later; FAILURE says not to try again.)
    }

    public void triggerNotification() {

        /*Random r = new Random();
        scheduleNotification(getApplicationContext(), 0, r.nextInt());*/
        //Get an instance of NotificationManager//
        Intent notifyIntent = new Intent(getApplicationContext(), PVTTest.class);
        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_ceasca_3)
                        .setContentTitle("Test PVT")
                        .setContentText("Fa urmatorul test.")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(notifyPendingIntent)
                        .setAutoCancel(true);


        // Gets an instance of the NotificationManager service//

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        // notificationId is a unique int for each notification that you must define
        Random r = new Random();
        notificationManager.notify(r.nextInt(), mBuilder.build());
    }
}

