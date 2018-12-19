package ro.fmi.ip.trei.coffeetracker.addrecord;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.main.MainActivity;

public class DetaliiActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "myPrefsFile";
    public static final String workTag = "notificationWork";
    private final String CHANNEL_ID = "ID";
    private final String CHANNEL_NAME = "Nume";
    private final int durataIntatziere = 3; //in secunde
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Bautura> bauturiList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private DatabaseReference mCoffeeReference;
    private DatabaseReference mTeaReference;
    private DatabaseReference mColaReference;
    private String cantitate;
    private String timestamp;
    private String oreDormite;
    private Boolean primul;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(AdaugareActivity.EXTRA_MESSAGE);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mCoffeeReference = FirebaseDatabase.getInstance().getReference("drinks").child("Coffee");
        mTeaReference = FirebaseDatabase.getInstance().getReference("drinks").child("Tea");
        mColaReference = FirebaseDatabase.getInstance().getReference("drinks").child("EnergyDrink");
        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String curDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String lastDate = (shared.getString("date", "No date defined"));
        if (lastDate.substring(0, 6).equals(curDate.substring(0, 6))) {
            primul = true;
        } else {
            primul = false;
        }
        createNotificationChannel();

        recyclerView = findViewById(R.id.recycler_view);

        mAdapter = new DetaliiAdapter(bauturiList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                String userPhoneNumber = user.getPhoneNumber();

                Bautura bautura = bauturiList.get(position);
                Context context = DetaliiActivity.this;
//                String key = mDatabase.child("records").child("+40727138440").push().getKey();
                String key = mDatabase.child("records").child(userPhoneNumber).push().getKey();
                if (!primul) {
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("date", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                    editor.apply();

                    AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                    EditText inputOre = new EditText(context);
                    inputOre.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    LinearLayout layout2 = new LinearLayout(context);
                    builder2.setTitle("Numărul de ore dormite");
                    inputOre.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(2);
                    inputOre.setFilters(FilterArray);
                    layout2.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    inputOre.setLayoutParams(lp);
                    layout2.addView(inputOre);
                    builder2.setView(layout2);

                    builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            oreDormite = inputOre.getText().toString();
                            bautura.setOreDormite(Double.parseDouble(oreDormite));

                            Map<String, Object> messageValues = bautura.toMap();
                            Map<String, Object> childUpdates = new HashMap<>();
//                            childUpdates.put("/records/" + "+40727138440" + "/" + key, messageValues);
                            childUpdates.put("/records/" + userPhoneNumber + "/" + key, messageValues);
                            mDatabase.updateChildren(childUpdates);
                            //pop-up
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Cantitatea");

                            // Set up the input
                            final EditText inputCant = new EditText(context);
                            inputCant.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            final EditText inputTime = new EditText(context);

                            LinearLayout layout = new LinearLayout(context);
                            layout.setOrientation(LinearLayout.VERTICAL);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);

                            inputCant.setLayoutParams(lp);
                            inputTime.setLayoutParams(lp);

                            inputTime.setText(dateFormat.format(new Date()));

                            layout.addView(inputCant);
                            layout.addView(inputTime);
                            builder.setView(layout);

                            //codul comentat este cel care ia numarul de telefon al userului curent si pune in el o inregistrare noua
                            //cel necomentat adauga o inregistrare noua in contul lui gabi

                            //Get user data


                            // Set up the buttons
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    cantitate = inputCant.getText().toString();
                                    timestamp = inputTime.getText().toString();
                                    long timestampValue;
                                    try {
                                        timestampValue = dateFormat.parse(timestamp).getTime();
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        timestampValue = new Date().getTime();
                                    }
                                    bautura.setCantitate(Double.parseDouble(cantitate));
                                    bautura.setTimestamp(timestampValue);

                                    Map<String, Object> messageValues = bautura.toMap();
                                    Map<String, Object> childUpdates = new HashMap<>();

//                                    childUpdates.put("/records/" + "+40727138440" + "/" + key, messageValues);
                                    childUpdates.put("/records/" + userPhoneNumber + "/" + key, messageValues);
                                    mDatabase.updateChildren(childUpdates);

                                    sendNotification();

                                    Intent intent = new Intent(context, AdaugareActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        }
                    });
                    builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder2.show();
                } else {
                    //pop-up
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Cantitatea");

                    // Set up the input
                    final EditText inputCant = new EditText(context);
                    inputCant.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    final EditText inputTime = new EditText(context);

                    LinearLayout layout = new LinearLayout(context);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);

                    inputCant.setLayoutParams(lp);
                    inputTime.setLayoutParams(lp);

                    inputTime.setText(dateFormat.format(new Date()));

                    layout.addView(inputCant);
                    layout.addView(inputTime);
                    builder.setView(layout);

                    //codul comentat este cel care ia numarul de telefon al userului curent si pune in el o inregistrare noua
                    //cel necomentat adauga o inregistrare noua in contul lui gabi

                    //Get user data
//                FirebaseAuth auth = FirebaseAuth.getInstance();
//                FirebaseUser user = auth.getCurrentUser();
//                String userId = user.getUid();

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cantitate = inputCant.getText().toString();
                            timestamp = inputTime.getText().toString();
                            long timestampValue;
                            try {
                                timestampValue = dateFormat.parse(timestamp).getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                                timestampValue = new Date().getTime();
                            }
                            bautura.setCantitate(Double.parseDouble(cantitate));
                            bautura.setTimestamp(timestampValue);
                            Map<String, Object> messageValues = bautura.toMap();
                            Map<String, Object> childUpdates = new HashMap<>();
//                            childUpdates.put("/records/" + "+40727138440" + "/" + key, messageValues);
                            childUpdates.put("/records/" + userPhoneNumber + "/" + key, messageValues);
                            mDatabase.updateChildren(childUpdates);
                            sendNotification();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        if ("cafea".equals(message)) {
            //prepareCafea();
            mCoffeeReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> records = dataSnapshot.getChildren();

                    for (DataSnapshot record : records) {
                        Bautura r = record.getValue(Bautura.class);
                        bauturiList.add(r);
                    }
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if ("ceai".equals(message)) {
            //prepareCeai();
            mTeaReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> records = dataSnapshot.getChildren();

                    for (DataSnapshot record : records) {
                        Bautura r = record.getValue(Bautura.class);
                        bauturiList.add(r);
                    }
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if ("cola".equals(message)) {
            // prepareCola();
            mColaReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> records = dataSnapshot.getChildren();

                    for (DataSnapshot record : records) {
                        Bautura r = record.getValue(Bautura.class);
                        bauturiList.add(r);
                    }
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private String calculeazaTimp(Long seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24) + 2;
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);
        return hours + ": " + minute + ": " + second;
    }

    private Double mlToMgCofeinaCafea(Long cantitate) {
        return cantitate * 0.42;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification() {

        OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotifyWorker.class)
                .setInitialDelay(durataIntatziere, TimeUnit.SECONDS)
                .addTag(workTag)
                .build();
        WorkManager.getInstance().enqueue(notificationWork);
    }

}

