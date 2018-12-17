package ro.fmi.ip.trei.coffeetracker.main.dashboard;

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
import ro.fmi.ip.trei.coffeetracker.addrecord.AddRecordActivity;
import ro.fmi.ip.trei.coffeetracker.databinding.FragmentDashboardBinding;


public class DashboardFragment extends Fragment {

    private DashboardViewModel viewModel;
    private FragmentDashboardBinding binding;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);

        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddRecordActivity.class);
            startActivity(intent);
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        // TODO: Use the ViewModel
    }

}
