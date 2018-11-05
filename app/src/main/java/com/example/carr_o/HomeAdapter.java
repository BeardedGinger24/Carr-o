package com.example.carr_o;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.carr_o.model.VINDecode;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeItemViewHolder> {

    private ArrayList<VINDecode> carInfo;
//    final private ListItemClickListener mOnClickListener;

    public HomeAdapter(ArrayList<VINDecode> carInfo){
        this.carInfo = carInfo;
//        this.mOnClickListener = mOnClickListener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public HomeItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.home_item_row;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        HomeItemViewHolder viewHolder = new HomeItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( HomeItemViewHolder homeViewHolder, int i) {
        homeViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return carInfo.size();
    }

    class HomeItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView mCarYear;
        TextView mCarMake;
        TextView mCarModel;


        public HomeItemViewHolder(View itemView) {
            super(itemView);

            mCarYear = (TextView) itemView.findViewById(R.id.car_year);
            mCarMake = (TextView) itemView.findViewById(R.id.car_make);
            mCarModel = (TextView) itemView.findViewById(R.id.car_model);

            itemView.setOnClickListener(this);
        }

        void bind(int position){
            mCarYear.setText("Year: " + carInfo.get(position).getYear());
            mCarMake.setText("Make: " +carInfo.get(position).getMake());
            mCarModel.setText("Model: " + carInfo.get(position).getModel());
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
//            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

}
