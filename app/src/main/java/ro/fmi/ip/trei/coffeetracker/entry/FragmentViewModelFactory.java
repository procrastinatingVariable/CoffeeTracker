package ro.fmi.ip.trei.coffeetracker.entry;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import ro.fmi.ip.trei.coffeetracker.entry.signin.OtpViewModel;
import ro.fmi.ip.trei.coffeetracker.entry.signin.PhoneInsertViewModel;
import ro.fmi.ip.trei.coffeetracker.entry.signup.FillProfileViewModel;

public class FragmentViewModelFactory implements ViewModelProvider.Factory {

    private EntryViewModel flowViewModel;

    public FragmentViewModelFactory(@NonNull FragmentActivity activity) {
        EntryViewModel viewModel = ViewModelProviders.of(activity).get(EntryViewModel.class);
        this.flowViewModel = viewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PhoneInsertViewModel.class)) {
            return (T) new PhoneInsertViewModel(flowViewModel);
        } else if (modelClass.isAssignableFrom(OtpViewModel.class)) {
            return (T) new OtpViewModel(flowViewModel);
        } else if (modelClass.isAssignableFrom(FillProfileViewModel.class)) {
            return (T) new FillProfileViewModel(flowViewModel);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
