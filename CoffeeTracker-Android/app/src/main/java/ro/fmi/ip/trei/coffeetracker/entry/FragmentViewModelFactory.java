package ro.fmi.ip.trei.coffeetracker.entry;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import ro.fmi.ip.trei.coffeetracker.entry.signin.OtpFragmentViewModel;
import ro.fmi.ip.trei.coffeetracker.entry.signin.PhoneInsertFragmentViewModel;
import ro.fmi.ip.trei.coffeetracker.entry.signup.FillProfileFragment;
import ro.fmi.ip.trei.coffeetracker.entry.signup.FillProfileFragmentViewModel;

public class FragmentViewModelFactory implements ViewModelProvider.Factory {

    private EntryActivityViewModel flowViewModel;

    public FragmentViewModelFactory(@NonNull FragmentActivity activity) {
        EntryActivityViewModel viewModel = ViewModelProviders.of(activity).get(EntryActivityViewModel.class);
        this.flowViewModel = viewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PhoneInsertFragmentViewModel.class)) {
            return (T) new PhoneInsertFragmentViewModel(flowViewModel);
        } else if (modelClass.isAssignableFrom(OtpFragmentViewModel.class)) {
            return (T) new OtpFragmentViewModel(flowViewModel);
        } else if (modelClass.isAssignableFrom(FillProfileFragmentViewModel.class)) {
            return (T) new FillProfileFragmentViewModel(flowViewModel);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
