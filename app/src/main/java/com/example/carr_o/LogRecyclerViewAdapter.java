package com.example.carr_o;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carr_o.data.LogViewModel;
import com.example.carr_o.data.Log;

import java.text.DecimalFormat;
import java.util.List;

public class LogRecyclerViewAdapter extends RecyclerView.Adapter<LogRecyclerViewAdapter.LogViewHolder>{

    private final LayoutInflater mInflater;
    private List<Log> mLogs; // Cached copy of words
    private LogViewModel viewModel;

    public LogRecyclerViewAdapter(Context context, LogViewModel viewModel) {
        this.viewModel = viewModel;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_log_item, parent, false);
        return new LogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LogViewHolder holder, final int position) {
        holder.bind(position);
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

    class LogViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        private final TextView mDate;
        private final TextView mLocation;
        private final TextView mMiles;
        private final TextView mPrice;
        private final TextView mType;
        private final TextView mNotes;

        private LogViewHolder(View itemView) {
            super(itemView);


            mDate = (TextView) itemView.findViewById(R.id.maintenance_date);
            mLocation = (TextView) itemView.findViewById(R.id.maintenance_title);
            mMiles = (TextView) itemView.findViewById(R.id.mileage);
            mPrice = (TextView) itemView.findViewById(R.id.total_price);
            mType = (TextView) itemView.findViewById(R.id.maintenance_type);
            mNotes = (TextView) itemView.findViewById(R.id.maintenance_notes);
            itemView.setOnLongClickListener(this);
        }

        void bind(int position){
//            double cost = mLogs.get(position).getTotalPrice();
            DecimalFormat moneyformatter = new DecimalFormat("#,###.00");
            DecimalFormat milesformatter = new DecimalFormat("#,###");

            mDate.setText("Date: " + mLogs.get(position).getDate());
            mLocation.setText("Location: " + mLogs.get(position).getLocation());
            mMiles.setText("Mileage: " + milesformatter.format(mLogs.get(position).getMileage()));
            mPrice.setText("$ " + moneyformatter.format(mLogs.get(position).getTotalPrice()));
            mType.setText("Maintenance Type: " + mLogs.get(position).getMaintType());
            mNotes.setText("Notes: " + mLogs.get(position).getNotes());

        }

        @Override
        public boolean onLongClick(View v) {
            int clickedPosition = getAdapterPosition();
            Log log = mLogs.get(clickedPosition);
            viewModel.delete(log);
            return false;
        }
    }

    public List<Log> getmLogs() {
        return mLogs;
    }

    public void setmLogs(List<Log> mLogs) {
        this.mLogs = mLogs;
    }

}
