package ro.fmi.ip.trei.coffeetracker.main.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.common.BaseFragment;
import ro.fmi.ip.trei.coffeetracker.databinding.FragmentProfileBinding;
import ro.fmi.ip.trei.coffeetracker.entry.EntryActivity;

public class ProfileFragment extends Fragment {

    private ProfileViewModel viewModel;
    private FragmentProfileBinding binding;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        binding.signOut.setOnClickListener(v -> {
            viewModel.signOut();
        });

        subscribeToViewModel();

        return binding.getRoot();
    }

    private void subscribeToViewModel() {
        viewModel.signOutEvent.observe(this, v -> {
            Intent intent = new Intent(getContext(), EntryActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
    }

}
