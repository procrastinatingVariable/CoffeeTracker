package ro.fmi.ip.trei.coffeetracker.addrecord;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ro.fmi.ip.trei.coffeetracker.R;

public class DetailsActivity extends AppCompatActivity {

    private static final String DUMMY_COFFEE_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/A_small_cup_of_coffee.JPG/275px-A_small_cup_of_coffee.JPG";
    private static final String DUMMY_COLA_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cf/Tumbler_of_cola_with_ice.jpg/220px-Tumbler_of_cola_with_ice.jpg";
    private static final String DUMMY_TEA_IMAGE = "https://img.etimg.com/thumb/msid-64274586,width-643,imgsize-179899,resizemode-4/tea-leaf-extract-is-the-new-answer-to-destroy-lung-cancer-cells.jpg";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Beverage> bauturiList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private String cantitate;
    private String timestamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(AddRecordActivity.EXTRA_MESSAGE);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recycler_view);

        mAdapter = new DetailsAdapter(bauturiList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Beverage bautura = bauturiList.get(position);
                Toast.makeText(getApplicationContext(), bautura.getDenumire() + " is selected!", Toast.LENGTH_SHORT).show();
                Context context = DetailsActivity.this;

                //pop-up
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cantitatea");

                // Set up the input
                final EditText inputCant = new EditText(context);
                final EditText inputTime = new EditText(context);

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);

                inputCant.setLayoutParams(lp);
                inputTime.setLayoutParams(lp);
                inputCant.setInputType(InputType.TYPE_CLASS_TEXT);

                Long tsLong = System.currentTimeMillis()/1000;
                inputTime.setText(calculeazaTimp(tsLong));

                layout.addView(inputCant);
                layout.addView(inputTime);
                builder.setView(layout);

                //codul comentat este cel care ia numarul de telefon al userului curent si pune in el o inregistrare noua
                //cel necomentat adauga o inregistrare noua in contul lui gabi

                //Get user data
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                String phoneNumber = user.getPhoneNumber();

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cantitate = inputCant.getText().toString();
                        timestamp = inputTime.getText().toString();
                        bautura.setDozaj(cantitate);
                        bautura.setUrlImagine(timestamp);

                        Map<String, Object> messageValues = bautura.toMap();
                        Map<String, Object> childUpdates = new HashMap<>();

//                        String key = mDatabase.child("records").child("+40727138440").push().getKey();
//                        childUpdates.put("/records/" + "+40727138440" + "/" + key, messageValues);
                        String key = mDatabase.child("records").child(phoneNumber).push().getKey();
                        childUpdates.put("/records/" + phoneNumber + "/" + key, messageValues);
                        mDatabase.updateChildren(childUpdates);

                        Intent intent = new Intent(context, AddRecordActivity.class);
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

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        if ("cafea".equals(message)) {
            prepareCafea();
        } else if ("ceai".equals(message)) {
            prepareCeai();
        } else if ("cola".equals(message)) {
            prepareCola();
        }
    }

    private String calculeazaTimp(Long seconds) {
        int day = (int)TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24) + 2;
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
        return hours + ": " + minute + ": " + second;
    }

    private void prepareCafea() {
        Beverage bautura = new Beverage("Espresso", "40mg", DUMMY_COFFEE_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Americano", "80mg", DUMMY_COFFEE_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Cappuccino", "80mg", DUMMY_COFFEE_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Latte", "80mg", DUMMY_COFFEE_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Mocha", "80mg", DUMMY_COFFEE_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Caffe con Leche", "60mg", DUMMY_COFFEE_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Caffe Breve", "80mg", DUMMY_COFFEE_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Red eye", "200mg", DUMMY_COFFEE_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Espresso Macchiato", "120mg", DUMMY_COFFEE_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Drip Coffee", "120mg", DUMMY_COFFEE_IMAGE);
        bauturiList.add(bautura);

        mAdapter.notifyDataSetChanged();
    }

    private void prepareCola() {
        Beverage bautura = new Beverage("Coca-Cola", "34mg", DUMMY_COLA_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Pepsi", "37.5mg", DUMMY_COLA_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Pepsi Twist", "37.5mg", DUMMY_COLA_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Diet Pepsi", "36.0mg", DUMMY_COLA_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Diet Pepsi Twist", "36.0mg", DUMMY_COLA_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Mountain Dew", "55mg", DUMMY_COLA_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Red Bull", "80.0mg", DUMMY_COLA_IMAGE);
        bauturiList.add(bautura);

        mAdapter.notifyDataSetChanged();
    }

    private void prepareCeai() {
        Beverage bautura = new Beverage("White Tea", "40mg", DUMMY_TEA_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Green Tea", "60mg", DUMMY_TEA_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Oolong Tea", "70mg", DUMMY_TEA_IMAGE);
        bauturiList.add(bautura);

        bautura = new Beverage("Black Tea", "80mg", DUMMY_TEA_IMAGE);
        bauturiList.add(bautura);

        mAdapter.notifyDataSetChanged();
    }
}

