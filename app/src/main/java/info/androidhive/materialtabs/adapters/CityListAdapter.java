package info.androidhive.materialtabs.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.androidhive.materialtabs.R;
import info.androidhive.materialtabs.listeners.RecyclerViewItemClickListener;
import info.androidhive.materialtabs.model.City;

/**
 * Created by Rully on 25/10/2016.
 */

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> {
    private RecyclerViewItemClickListener recyclerViewItemClickListener ;
    private List<City> cityList ;

    public CityListAdapter(List<City> cityList) {
        this.cityList = cityList;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_city, parent, false);

        final CityViewHolder cityViewHolder = new CityViewHolder(itemView) ;

        cityViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPos = cityViewHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerViewItemClickListener != null) {
                        recyclerViewItemClickListener.onItemClick(adapterPos, cityViewHolder.itemView);
                    }
                }
            }
        });

        return cityViewHolder ;
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        City city = cityList.get(position);

        holder.id.setText(city.getId());
        holder.city.setText(city.getKota());
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public City getItem(int positon){
        return cityList.get(positon);
    }

    public void remove(City item) {
        int position = cityList.indexOf(item);
        if (position > -1) {
            cityList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }
    public void setOnItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener ;
    }


    public class CityViewHolder extends RecyclerView.ViewHolder {

        TextView id, city ;

        public CityViewHolder(View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.id_city);
            city = (TextView) itemView.findViewById(R.id.city);
        }
    }


}
