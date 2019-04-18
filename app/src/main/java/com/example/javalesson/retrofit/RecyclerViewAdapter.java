package com.example.javalesson.retrofit;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<Results> rData;
    private LayoutInflater mInflater;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Results> data) {
        this.mInflater = LayoutInflater.from(context);
        this.rData = data;
        this.context = context;
    }




    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.recycler_view_row, viewGroup, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String strId;
        String strTitle;
        strId = rData.get(position).getId();
        strTitle = rData.get(position).getTitle();
        holder.textViewTitle.setText(strTitle);
        holder.textViewId.setText(strId);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return rData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewId;
        private TextView textViewTitle;
        private FrameLayout frameLayout;


        ViewHolder(View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.recycler_view_row_id_text);
            textViewTitle = itemView.findViewById(R.id.recycler_view_row_id_title);
            frameLayout = itemView.findViewById(R.id.information_frag);

        }

    }
}
