package ro.fmi.ip.trei.coffeetracker.entry.signup;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.databinding.FragmentFillProfileBinding;
import ro.fmi.ip.trei.coffeetracker.entry.EntryActivityViewModel;
import ro.fmi.ip.trei.coffeetracker.entry.FragmentViewModelFactory;
import ro.fmi.ip.trei.coffeetracker.entry.signin.PhoneInsertFragmentViewModel;

public class FillProfileFragment extends Fragment {

    private FragmentFillProfileBinding binding;
    private FillProfileFragmentViewModel viewModel;
    private EntryActivityViewModel flowViewModel;



    // === Lifecycle ===

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fill_profile, container, false);

        initView();

        bindFields();

        bindButtons();

        return binding.getRoot();

    }


    // ^^^ Lifecycle ^^^

    private void bindButtons() {
        binding.saveButton.setOnClickListener(v -> {
            viewModel.saveState();
            viewModel.registerUser();
            flowViewModel.goToMainScreen();
        });
    }

    private void bindFields() {
        flowViewModel = ViewModelProviders.of(getActivity()).get(EntryActivityViewModel.class);
        ViewModelProvider.Factory factory = new FragmentViewModelFactory(getActivity());
        viewModel = ViewModelProviders.of(this, factory).get(FillProfileFragmentViewModel.class);
        binding.setViewModel(viewModel);
    }

    private void initView() {
    }

}
