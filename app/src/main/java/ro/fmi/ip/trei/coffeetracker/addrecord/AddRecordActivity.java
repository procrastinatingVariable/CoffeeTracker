package ro.fmi.ip.trei.coffeetracker.addrecord;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.addrecord.model.Beverage;
import ro.fmi.ip.trei.coffeetracker.common.BaseActivity;
import ro.fmi.ip.trei.coffeetracker.databinding.ActivityAddRecordBinding;
import ro.fmi.ip.trei.coffeetracker.ui.dialogs.DatePickerFragment;
import ro.fmi.ip.trei.coffeetracker.ui.dialogs.TimePickerFragment;

public class AddRecordActivity extends BaseActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    public static final int RESULT_CODE = 1;
    public static final String RESULT_KEY_BEVERAGE = "beverage";
    public static final String RESULT_KEY_TIMESTAMP = "timestamp";
    public static final String RESULT_KEY_AMOUNT = "amount";

    private DialogFragment timePicker;
    private DialogFragment datePicker;

    private ActivityAddRecordBinding binding;
    private AddRecordViewModel viewModel;

    private BeverageListPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.time.setOnClickListener(v -> displayTimePicker());
        binding.date.setOnClickListener(v -> displayDatePicker());

        initDateAndTimeFields();

        setupTabs();

        style();
    }

    private void style() {
        ActionBar actionbar = getSupportActionBar();
        actionbar.setElevation(0);
        actionbar.setTitle(R.string.add_record_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_record_confirm_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        checkAndConfirmSelection();
        return true;
    }

    private void checkAndConfirmSelection() {
        Beverage selectedBeverage = getSelectedBeverage();
        if (selectedBeverage == null) {
            Toast.makeText(this, R.string.error_beverage_not_selected, Toast.LENGTH_SHORT).show();
            return;
        }

        String date = binding.date.getText().toString();
        String time = binding.time.getText().toString();
        Date timestamp = toTimestamp(date, time);
        int amount = binding.sizeSelector.getSelectedAmount();
        if (amount == 0) {
            Toast.makeText(this, R.string.error_amount_not_selected, Toast.LENGTH_SHORT).show();
            return;
        }

        sendBackResultAndFinish(selectedBeverage, timestamp, amount);
    }

    private Date toTimestamp(String date, String time) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            return format.parse(date + " " + time);
        } catch (ParseException e) {
            return new Date();
        }
    }

    private void sendBackResultAndFinish(Beverage beverage, Date timestamp, int amount) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_KEY_BEVERAGE, beverage);
        intent.putExtra(RESULT_KEY_TIMESTAMP, timestamp);
        intent.putExtra(RESULT_KEY_AMOUNT, amount);
        setResult(RESULT_CODE, intent);
        finish();
    }

    private Beverage getSelectedBeverage() {
        int currentScreenIndex = binding.viewPager.getCurrentItem();
        BeverageListFragment currentFragment = (BeverageListFragment)viewPagerAdapter.getItem(currentScreenIndex);
        return currentFragment.getSelectedBeverage();
    }

    private void setupTabs() {
        viewPagerAdapter = new BeverageListPagerAdapter(getSupportFragmentManager());
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);
    }

    @Override
    protected void subscribeToViewModel() {

    }

    @Override
    protected void bindFields() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_record);
        viewModel = ViewModelProviders.of(this).get(AddRecordViewModel.class);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        setTime(hourOfDay, minute);
    }

    private void setTime(int hourOfDay, int minute) {
        String timeFormat = "%02d:%02d";
        String time = String.format(timeFormat, hourOfDay, minute);

        binding.time.setText(time);
    }

    private void displayTimePicker() {
        if (timePicker == null) {
            timePicker = new TimePickerFragment();
        }
        timePicker.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        setDate(year, month, dayOfMonth);
    }

    private void setDate(int year, int month, int dayOfMonth) {
        String dateFormat = "%02d/%02d/%d";
        String date = String.format(dateFormat, dayOfMonth, month, year);

        binding.date.setText(date);
    }

    private void displayDatePicker() {
        if (datePicker == null) {
            datePicker = new DatePickerFragment();
        }
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    private void initDateAndTimeFields() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        setTime(hour, minute);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        setDate(year, month, day);
    }

}
