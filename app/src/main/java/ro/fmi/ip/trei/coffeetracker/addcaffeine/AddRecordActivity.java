package ro.fmi.ip.trei.coffeetracker.addcaffeine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import ro.fmi.ip.trei.coffeetracker.R;

public class AddRecordActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "ro.fmi.ip.trei.coffeetracker";

    public ImageButton imageButtonCafea;
    public ImageButton imageButtonCeai;
    public ImageButton imageButtonCola;
    public String optiune = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecord);
        imageButtonCafea = findViewById(R.id.image_button_cafea);
        imageButtonCeai = findViewById(R.id.image_button_ceai);
        imageButtonCola = findViewById(R.id.image_button_cola);

        imageButtonCafea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddRecordActivity.this, "Cafea", Toast.LENGTH_LONG).show();
                optiune = "cafea";
                adaugaBautura(v);
            }
        });
        imageButtonCeai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddRecordActivity.this, "Ceai", Toast.LENGTH_LONG).show();
                optiune = "ceai";
                adaugaBautura(v);
            }
        });
        imageButtonCola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddRecordActivity.this, "Cola", Toast.LENGTH_LONG).show();
                optiune = "clipart_cola";
                adaugaBautura(v);
            }
        });
    }
    public void adaugaBautura(View view) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, optiune);
        startActivity(intent);
    }
}
