package ro.fmi.ip.trei.coffeetracker.main.records;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.databinding.FragmentRecordsBinding;
import ro.fmi.ip.trei.coffeetracker.main.model.Record;

public class RecordsFragment extends Fragment {

    private RecordsViewModel viewModel;
    private FragmentRecordsBinding binding;

    private RecordsAdapter recordsAdapter;

    public static RecordsFragment newInstance() {
        return new RecordsFragment();
    }

    // === Lifecycle ===

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recordsAdapter = new RecordsAdapter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_records, container, false);
        viewModel = ViewModelProviders.of(this).get(RecordsViewModel.class);

        binding.recordList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recordList.setAdapter(recordsAdapter);

        subscribeToViewModel();

        viewModel.loadData();

        return binding.getRoot();
    }


    // ^^^ Lifecycle ^^^

    private void subscribeToViewModel() {
        viewModel.recordItems.observe(this, records -> recordsAdapter.updateData(records));
    }


}
