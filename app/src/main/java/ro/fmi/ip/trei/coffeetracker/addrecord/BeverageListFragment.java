package ro.fmi.ip.trei.coffeetracker.addrecord;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.addrecord.model.Beverage;
import ro.fmi.ip.trei.coffeetracker.databinding.FragmentBeverageListBinding;

public class BeverageListFragment extends Fragment {

    public enum Type {
        COFFEE,
        TEA,
        SOFT_DRINK
    }

    private static final String KEY_TYPE = "TYPE";
    public static BeverageListFragment newInstance(Type type) {
        BeverageListFragment fragment = new BeverageListFragment();

        Bundle args = new Bundle(1);
        args.putSerializable(KEY_TYPE, type);
        fragment.setArguments(args);

        return fragment;
    }

    private FragmentBeverageListBinding binding;

    private BeverageAdapter listAdapter;

    private Type type;

    public BeverageListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        determineType();
        populateBasedOnType();
    }
    
    private void populateBasedOnType() {
        switch (type) {
            case COFFEE:
                listAdapter = new BeverageAdapter(Arrays.asList(
                        new Beverage("https://bit.ly/2pzCPjo", "Americano", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Americano", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Americano", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Americano", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Americano", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Americano", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Americano", 20)
                ));
                break;
                
            case TEA:
                listAdapter = new BeverageAdapter(Arrays.asList(
                        new Beverage("https://bit.ly/2pzCPjo", "Black Tea", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Black Tea", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Black Tea", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Black Tea", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Black Tea", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Black Tea", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Black Tea", 20)
                ));
                break;
                
            case SOFT_DRINK:
                listAdapter = new BeverageAdapter(Arrays.asList(
                        new Beverage("https://bit.ly/2pzCPjo", "Coca Cola", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Coca Cola", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Coca Cola", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Coca Cola", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Coca Cola", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Coca Cola", 20),
                        new Beverage("https://bit.ly/2pzCPjo", "Coca Cola", 20)
                ));
                break;
        }
    }

    private void determineType() {
        Bundle args = getArguments();
        type = (Type)args.getSerializable(KEY_TYPE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_beverage_list, container, false);
        initList();
        return binding.getRoot();
    }

    private void initList() {
        binding.beverageList.setAdapter(listAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        binding.beverageList.setLayoutManager(layoutManager);
    }

    public Beverage getSelectedBeverage() {
        return listAdapter.getSelectedBeverage();
    }
}
