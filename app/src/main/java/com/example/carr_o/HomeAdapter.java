package com.example.carr_o;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.carr_o.data.Log;
import com.example.carr_o.data.LogViewModel;
import com.example.carr_o.fragment.HomeFragment;
import com.example.carr_o.model.VINDecode;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Log> mLogs; // Cached copy of words
    private LogViewModel viewModel;
//    final private ListItemClickListener mOnClickListener;

    private ArrayList<VINDecode> carInfo;
//    final private ListItemClickListener mOnClickListener;

//    public HomeAdapter(ArrayList<VINDecode> carInfo){
//        this.carInfo = carInfo;
////        this.mOnClickListener = mOnClickListener;
//    }

    public HomeAdapter(Context context, LogViewModel viewModel) {
        this.viewModel = viewModel;
        mInflater = LayoutInflater.from(context);
//        this.mOnClickListener = mOnClickListener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public void setLogs(List<Log> logs){
        mLogs = logs;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mLogs != null)
            return mLogs.size();
        else return 0;
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

    class HomeItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView mMaintType;
        TextView mLocation;
        TextView mMileage;
        Button mComplete;


        public HomeItemViewHolder(View itemView) {
            super(itemView);

            mMaintType = (TextView) itemView.findViewById(R.id.maintenance_type_home);
            mLocation = (TextView) itemView.findViewById(R.id.maintenance_location_home);
            mMileage = (TextView) itemView.findViewById(R.id.mileage_home);
            mComplete = (Button) itemView.findViewById(R.id.bt_complete);
//            mComplete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ((ListView) parent).performItemClick(v, position, 0);
//                }
//            });

            itemView.setOnClickListener(this);
        }

        void bind(int position){
            mMaintType.setText("Maintenance Type: " + mLogs.get(position).getMaintType());
            mLocation.setText("Location: " + mLogs.get(position).getLocation());
            mMileage.setText("Mileage: " + mLogs.get(position).getMileage());
            mComplete.setTag(position);
            mComplete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
//            mOnClickListener.onListItemClick(clickedPosition);
            if(v.getId() == mComplete.getId()) {
                Log log = mLogs.get(clickedPosition);
                viewModel.update(log);
            }
        }
    }

}
