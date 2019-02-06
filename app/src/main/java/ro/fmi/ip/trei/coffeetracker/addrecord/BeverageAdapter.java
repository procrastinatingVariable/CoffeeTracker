package ro.fmi.ip.trei.coffeetracker.addrecord;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.addrecord.model.Beverage;

class BeverageAdapter extends RecyclerView.Adapter<BeverageAdapter.ViewHolder> {

    private List<Beverage> data;
    private Beverage selectedBeverage;
    private ViewHolder lastSelected;

    public BeverageAdapter() {
        this.data = new ArrayList<>();
    }

    public BeverageAdapter(List<Beverage> data) {
        this.data = data;
    }

    public void setData(List<Beverage> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rvitem_beverage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Beverage beverage = data.get(i);
        viewHolder.setName(beverage.name);
        viewHolder.setConcentration(beverage.caffeineConcentration);
        viewHolder.setImage(beverage.imageUrl);
        viewHolder.setOnSelectionListener(v -> {
            if (lastSelected != null) {
                lastSelected.markAsUnselected();
            }
            lastSelected = v;
            lastSelected.markAsSelected();
            selectedBeverage = v.getBeverage();
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public Beverage getSelectedBeverage() {
        return selectedBeverage;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        interface OnSelectionListener {
            void onSelect(ViewHolder viewHolder);
        }

        private String imageUrl;
        private String name;
        private int concentration;

        private ImageView imageImageView;
        private TextView nameTextView;
        private TextView concentrationTextView;
        private ViewGroup namePlate;

        private OnSelectionListener selectionListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageImageView = itemView.findViewById(R.id.image);
            nameTextView = itemView.findViewById(R.id.name);
            concentrationTextView = itemView.findViewById(R.id.concentration);
            namePlate = itemView.findViewById(R.id.namePlate);
            itemView.setOnClickListener(v -> {
                if (selectionListener != null) {
                    selectionListener.onSelect(ViewHolder.this);
                }
            });
        }

        public void setImage(String imageUrl) {
            this.imageUrl = imageUrl;
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.progress_animation)
                    .into(imageImageView);
        }

        public void setName(String name) {
            this.name = name;
            nameTextView.setText(name);
        }

        public void setConcentration(int concentration) {
            this.concentration = concentration;
            concentrationTextView.setText(Integer.toString(concentration));
        }

        public void setOnSelectionListener(OnSelectionListener listener) {
            this.selectionListener = listener;
        }

        public void markAsSelected() {
            namePlate.setBackgroundResource(R.drawable.rvitem_beverage_nameplate_selected);
        }

        public void markAsUnselected() {
            namePlate.setBackgroundResource(R.drawable.rvitem_beverage_nameplate_unselected);
        }

        public Beverage getBeverage() {
            return new Beverage(imageUrl, name, concentration);
        }
    }
}
