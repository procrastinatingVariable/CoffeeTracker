package ro.fmi.ip.trei.coffeetracker.addcaffeine;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ro.fmi.ip.trei.coffeetracker.R;

public class DetailsActivity extends AppCompatActivity {

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
//                FirebaseAuth auth = FirebaseAuth.getInstance();
//                FirebaseUser user = auth.getCurrentUser();
//                String userId = user.getUid();

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

                        String key = mDatabase.child("records").child("+40727138440").push().getKey();
                        childUpdates.put("/records/" + "+40727138440" + "/" + key, messageValues);
//                        String key = mDatabase.child("records").child(userId).push().getKey();
//                        childUpdates.put("/records/" + userId + "/" + key, messageValues);
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
        Beverage bautura = new Beverage("Copt rău", "1", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "2", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "3", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "4", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "5", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);

        mAdapter.notifyDataSetChanged();
    }

    private void prepareCeai() {
        Beverage bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);

        mAdapter.notifyDataSetChanged();
    }

    private void prepareCola() {
        Beverage bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);
        bautura = new Beverage("Copt rău", "", "");
        bauturiList.add(bautura);

        mAdapter.notifyDataSetChanged();
    }
}

