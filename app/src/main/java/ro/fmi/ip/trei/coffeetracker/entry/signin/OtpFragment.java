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
import ro.fmi.ip.trei.coffeetracker.databinding.FragmentOtpBinding;
import ro.fmi.ip.trei.coffeetracker.entry.EntryViewModel;
import ro.fmi.ip.trei.coffeetracker.entry.FragmentViewModelFactory;

public class OtpFragment extends Fragment {

    public static final String DEBUG_TAG = OtpFragment.class.getSimpleName();



    private EntryViewModel flowViewModel;
    private OtpViewModel viewModel;
    private FragmentOtpBinding binding;


    // === Lifecycle ===

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_otp, container, false);

        initView();

        bindFields();

        bindButtons();

        return binding.getRoot();
    }

    // ^^^ Lifecycle ^^^

    private void bindFields() {
        flowViewModel = ViewModelProviders.of(getActivity()).get(EntryViewModel.class);
        ViewModelProvider.Factory factory = new FragmentViewModelFactory(getActivity());
        viewModel = ViewModelProviders.of(this, factory).get(OtpViewModel.class);
        binding.setViewModel(viewModel);
    }

    private void initView() {

    }

    private void bindButtons() {
        binding.verifyButton.setOnClickListener(v -> {
            viewModel.signIn();
        });
    }


}
