package ro.fmi.ip.trei.coffeetracker.main.dashboard;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.custom.FirebaseModelDataType;
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.custom.FirebaseModelOptions;
import com.google.firebase.ml.custom.FirebaseModelOutputs;

import java.util.ArrayList;
import java.util.List;

import ro.fmi.ip.trei.coffeetracker.CoffeeTracker;
import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.addrecord.AdaugareActivity;
import ro.fmi.ip.trei.coffeetracker.databinding.FragmentDashboardBinding;
import ro.fmi.ip.trei.coffeetracker.entry.model.Formats;
import ro.fmi.ip.trei.coffeetracker.main.model.Record;


public class DashboardFragment extends Fragment {

    private static final String TAG_DEBUG = DashboardFragment.class.getSimpleName();


    private DashboardViewModel viewModel;
    private FragmentDashboardBinding binding;

    private FirebaseModelInterpreter modelInterpreter;
    private FirebaseModelInputOutputOptions modelIOOptions;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            modelIOOptions = new FirebaseModelInputOutputOptions.Builder()
                    .setInputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 2})
                    .setOutputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 1})
                    .build();
        } catch (FirebaseMLException e) {
            modelIOOptions = null;
            e.printStackTrace();
        }
        FirebaseModelOptions options = new FirebaseModelOptions.Builder()
                .setCloudModelName(CoffeeTracker.FIREBASE_ML_MODEL)
                .build();
        try {
            modelInterpreter = FirebaseModelInterpreter.getInstance(options);
        } catch (FirebaseMLException e) {
            modelInterpreter = null;
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        this.viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);

        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AdaugareActivity.class);
            startActivity(intent);
        });

        subscribeToViewModel();

        initCharts();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.fetchCondensedRecords();
        float[][] input = new float[][]{{10, 20}};
        FirebaseModelInputs inputs = null;
        try {
            inputs = new FirebaseModelInputs.Builder()
                    .add(input)
                    .build();
            modelInterpreter.run(inputs, modelIOOptions)
                    .addOnCompleteListener(new OnCompleteListener<FirebaseModelOutputs>() {
                        @Override
                        public void onComplete(@NonNull Task<FirebaseModelOutputs> task) {
                            if (task.isSuccessful()) {
                                FirebaseModelOutputs firebaseModelOutputs = task.getResult();
                                float[][] output = firebaseModelOutputs.getOutput(0);
                                Log.d(TAG_DEBUG, "Output is: " + output[0][0]);
                            } else {
                                Exception e = task.getException();
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (FirebaseMLException e) {
            e.printStackTrace();
        }
    }

    private void subscribeToViewModel() {
        viewModel.data.observe(this, data -> {
            chartSetData(data);
        });
    }

    private void initCharts() {
        binding.chartCaffeine.setDescription(null);
        binding.chartCaffeine.getLegend().setEnabled(false);

        binding.chartCaffeine.setScaleYEnabled(false);
        binding.chartCaffeine.setDragYEnabled(false);

        binding.chartCaffeine.setHighlightPerDragEnabled(false);
        binding.chartCaffeine.setHighlightPerTapEnabled(false);

        XAxis xAxis = binding.chartCaffeine.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);

        YAxis yAxisRight = binding.chartCaffeine.getAxisRight();
        yAxisRight.setEnabled(false);

        YAxis yAxisLeft = binding.chartCaffeine.getAxisLeft();
        yAxisLeft.setDrawAxisLine(false);
    }

    private void chartSetData(List<Record> data) {
        List<Entry> plotData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            Record r = data.get(i);
            plotData.add(new Entry(i, (float)(r.getQuantity() * r.getDosage()) ));
        }

        binding.chartCaffeine.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int)value;
                if (index >= data.size()) {
                    return "";
                }

                Record r = data.get(index);
                return Formats.getDayFormat().format(r.getRegistrationDate());
            }
        });

        LineDataSet dataSet = new LineDataSet(plotData, "label");
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.GREEN);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setColor(Color.GREEN);


        LineData lineData = new LineData(dataSet);
        binding.chartCaffeine.setData(lineData);
        binding.chartCaffeine.invalidate();
        binding.chartCaffeine.zoom(0f, 1f, 0, 0);
    }

}
