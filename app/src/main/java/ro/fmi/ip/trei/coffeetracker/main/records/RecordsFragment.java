package ro.fmi.ip.trei.coffeetracker.main.records;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.databinding.FragmentRecordsBinding;

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recordList.setLayoutManager(layoutManager);
        binding.recordList.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        binding.recordList.setAdapter(recordsAdapter);

        subscribeToViewModel();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadData();
    }

    // ^^^ Lifecycle ^^^

    private void subscribeToViewModel() {
        viewModel.recordItems.observe(this, resource -> {
            if (resource == null) {
                return;
            }

            switch(resource.status) {
                case LOADING:
                    showPreloader();
                    break;

                case SUCCESS:
                    hidePreloader();
                    recordsAdapter.updateData(resource.data);
                    break;

                case ERROR:
                    break;
            }
        });
    }

    private void showPreloader() {
        binding.preloader.setVisibility(View.VISIBLE);
    }

    private void hidePreloader() {
        binding.preloader.setVisibility(View.INVISIBLE);
    }

}
