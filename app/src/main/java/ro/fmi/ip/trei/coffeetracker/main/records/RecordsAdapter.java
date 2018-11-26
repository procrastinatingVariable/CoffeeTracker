package ro.fmi.ip.trei.coffeetracker.main.records;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.databinding.ItemRecordBinding;
import ro.fmi.ip.trei.coffeetracker.main.model.Formats;
import ro.fmi.ip.trei.coffeetracker.main.model.Record;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.RecordItemViewHolder> {

    private List<Record> data;
    private LayoutInflater layoutInflater;

    public RecordsAdapter() {
        data = new ArrayList<>();
    }

    public RecordsAdapter(List<Record> data) {
        this.data = data;
    }

    public void updateData(List<Record> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecordItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }

        ItemRecordBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_record, viewGroup, false);

        return new RecordItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordItemViewHolder viewHolder, int position) {
        viewHolder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class RecordItemViewHolder extends RecyclerView.ViewHolder {

        private ItemRecordBinding binding;

        RecordItemViewHolder(@NonNull ItemRecordBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Record record) {
            binding.recordName.setText(record.getName());
            binding.recordQuantity.setText(Double.toString(record.getQuantity()));
            binding.recordDate.setText(Formats.getDateFormat().format(record.getRegistrationDate()));
        }
    }
}
