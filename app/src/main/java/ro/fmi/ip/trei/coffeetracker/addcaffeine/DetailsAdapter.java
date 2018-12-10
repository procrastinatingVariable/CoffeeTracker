package ro.fmi.ip.trei.coffeetracker.addcaffeine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ro.fmi.ip.trei.coffeetracker.R;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {

    private ArrayList<Beverage> bauturiList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView denumire, dozaj, urlImagine;

        public MyViewHolder(View view) {
            super(view);
            denumire = (TextView) view.findViewById(R.id.denumire);
            dozaj = (TextView) view.findViewById(R.id.dozaj);
            urlImagine = (TextView) view.findViewById(R.id.urlImagine);
        }
    }


    public DetailsAdapter(ArrayList<Beverage> bauturiList) {
        this.bauturiList = bauturiList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bautura_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Beverage bautura = bauturiList.get(position);
        holder.denumire.setText(bautura.getDenumire());
        holder.dozaj.setText(bautura.getDozaj());
        holder.urlImagine.setText(bautura.getUrlImagine());
    }

    @Override
    public int getItemCount() {
        return bauturiList.size();
    }

}
