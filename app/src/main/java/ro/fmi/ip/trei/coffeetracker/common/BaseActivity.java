package ro.fmi.ip.trei.coffeetracker.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    private Toast alertToast;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindFields();
        subscribeToViewModel();
    }

    protected abstract void subscribeToViewModel();

    protected abstract void bindFields();

    public void displayToastMessage(String message) {
        if (alertToast != null) {
            alertToast.cancel();
        }
        alertToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        alertToast.show();
    }

}
