package ro.fmi.ip.trei.coffeetracker.main.dashboard;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.addrecord.AdaugareActivity;
import ro.fmi.ip.trei.coffeetracker.databinding.FragmentDashboardBinding;
import ro.fmi.ip.trei.coffeetracker.entry.model.Formats;
import ro.fmi.ip.trei.coffeetracker.main.model.Record;


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
