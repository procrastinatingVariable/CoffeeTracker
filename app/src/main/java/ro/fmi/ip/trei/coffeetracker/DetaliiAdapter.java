package ro.fmi.ip.trei.coffeetracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DetaliiAdapter extends RecyclerView.Adapter<DetaliiAdapter.MyViewHolder> {

    private ArrayList<Bautura> bauturiList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView denumire, dozaj, urlImagine;

        public MyViewHolder(View view) {
            super(view);
            denumire = (TextView) view.findViewById(R.id.denumire);
            dozaj = (TextView) view.findViewById(R.id.dozaj);
            urlImagine = (TextView) view.findViewById(R.id.urlImagine);
        }
    }


    public DetaliiAdapter(ArrayList<Bautura> bauturiList) {
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
        Bautura bautura = bauturiList.get(position);
        holder.denumire.setText(bautura.getDenumire());
        holder.dozaj.setText(bautura.getDozaj());
        holder.urlImagine.setText(bautura.getUrlImagine());
    }

    @Override
    public int getItemCount() {
        return bauturiList.size();
    }

}
