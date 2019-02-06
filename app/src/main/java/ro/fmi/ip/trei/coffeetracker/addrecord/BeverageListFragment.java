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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.addrecord.model.Beverage;
import ro.fmi.ip.trei.coffeetracker.data.callbacks.GetBeverageCallback;
import ro.fmi.ip.trei.coffeetracker.data.firebase.FirebaseBeverageHelper;
import ro.fmi.ip.trei.coffeetracker.data.model.BeverageEntity;
import ro.fmi.ip.trei.coffeetracker.databinding.FragmentBeverageListBinding;
import ro.fmi.ip.trei.coffeetracker.util.ModelMapper;

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
        listAdapter = new BeverageAdapter();
        switch (type) {
            case COFFEE:
                FirebaseBeverageHelper.getInstance().getCoffeeRecords(new GetBeverageCallback() {
                    @Override
                    public void onResult(List<BeverageEntity> beverages) {
                        List<Beverage> data = ModelMapper.mapList(beverages, value -> new Beverage(value.getImageUrl(), value.getName(), value.getConcentration()));
                        listAdapter.setData(data);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                break;
                
            case TEA:
                FirebaseBeverageHelper.getInstance().getTeaRecords(new GetBeverageCallback() {
                    @Override
                    public void onResult(List<BeverageEntity> beverages) {
                        List<Beverage> data = ModelMapper.mapList(beverages, value -> new Beverage(value.getImageUrl(), value.getName(), value.getConcentration()));
                        listAdapter.setData(data);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                break;
                
            case SOFT_DRINK:
                FirebaseBeverageHelper.getInstance().getSoftDrinkRecords(new GetBeverageCallback() {
                    @Override
                    public void onResult(List<BeverageEntity> beverages) {
                        List<Beverage> data = ModelMapper.mapList(beverages, value -> new Beverage(value.getImageUrl(), value.getName(), value.getConcentration()));
                        listAdapter.setData(data);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
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
