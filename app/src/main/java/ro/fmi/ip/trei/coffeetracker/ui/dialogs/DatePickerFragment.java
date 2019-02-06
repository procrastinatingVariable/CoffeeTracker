package ro.fmi.ip.trei.coffeetracker.ui.dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

import ro.fmi.ip.trei.coffeetracker.databinding.ActivityAddRecordBinding;

public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener onDateSetListener;

    public DatePickerFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (activity instanceof DatePickerDialog.OnDateSetListener) {
            onDateSetListener = (DatePickerDialog.OnDateSetListener) activity;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        Activity hostActivity = getActivity();
        return new DatePickerDialog(hostActivity, onDateSetListener, year, month, day);
    }
}
