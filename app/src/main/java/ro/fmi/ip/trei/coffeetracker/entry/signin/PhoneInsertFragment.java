package ro.fmi.ip.trei.coffeetracker.entry.signin;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.databinding.FragmentPhoneInsertBinding;
import ro.fmi.ip.trei.coffeetracker.entry.EntryViewModel;
import ro.fmi.ip.trei.coffeetracker.entry.FragmentViewModelFactory;

public class PhoneInsertFragment extends Fragment implements PhoneInsertViewModel.PhoneNumberValidator {

    private static final String DEFAULT_COUNTY_CODE = "ro";



    private FragmentPhoneInsertBinding binding;
    private PhoneInsertViewModel viewModel;
    private EntryViewModel flowViewModel;

    // === Lifecycle ===

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_phone_insert, container, false);

        initView();

        bindFields();

        bindButtons();

        return binding.getRoot();
    }

    // ^^^ Lifecycle ^^^

    private void bindFields() {
        flowViewModel = ViewModelProviders.of(getActivity()).get(EntryViewModel.class);
        ViewModelProvider.Factory factory = new FragmentViewModelFactory(getActivity());
        viewModel = ViewModelProviders.of(this, factory).get(PhoneInsertViewModel.class);
        viewModel.setPhoneNumberValidator(this);
    }

    private void subscribeToViewModel() {
    }

    private void initView() {
        binding.countrCodePicker.setCountryForNameCode(DEFAULT_COUNTY_CODE);
        binding.countrCodePicker.registerCarrierNumberEditText(binding.phoneField);
    }

    private void bindButtons() {
        binding.nextButton.setOnClickListener(v -> {
            String phoneNumber = binding.countrCodePicker.getFullNumberWithPlus();
            viewModel.setPhoneNumber(phoneNumber);
            viewModel.validatePhoneNumber();
        });
    }

    // === PhoneInsertViewModel.PhoneNumberValidator ===

    @Override
    public boolean validate(String phoneNumber) {
        return binding.countrCodePicker.isValidFullNumber();
    }

    // ^^^ PhoneInsertViewModel.PhoneNumberValidator ^^^



}
