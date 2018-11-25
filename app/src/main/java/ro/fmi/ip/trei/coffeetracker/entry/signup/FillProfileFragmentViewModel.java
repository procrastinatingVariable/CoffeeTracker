package ro.fmi.ip.trei.coffeetracker.entry.signup;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ro.fmi.ip.trei.coffeetracker.entry.EntryActivityViewModel;
import ro.fmi.ip.trei.coffeetracker.entry.LoginState;
import ro.fmi.ip.trei.coffeetracker.entry.model.Formats;

public class FillProfileFragmentViewModel extends ViewModel {

    private EntryActivityViewModel flowViewModel;

    public final MutableLiveData<String> firstName = new MutableLiveData<>();
    public final MutableLiveData<String> lastName = new MutableLiveData<>();
    public final MutableLiveData<String> birthDate = new MutableLiveData<>();


    public FillProfileFragmentViewModel(EntryActivityViewModel viewModel) {
        this.flowViewModel = viewModel;
    }

    public void saveState() {
        flowViewModel.getLoginStateBuilder()
                .firstName(firstName.getValue())
                .lastName(lastName.getValue())
                .birthDate(convertToDate(birthDate.getValue()));
    }

    public void registerUser() {
        flowViewModel.registerUser();
    }

    private Date convertToDate(String dateString) {
        try {
            return Formats.getDateFormat().parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}
