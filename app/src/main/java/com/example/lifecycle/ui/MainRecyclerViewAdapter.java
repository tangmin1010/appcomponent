package com.example.lifecycle.ui;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lifecycle.R;
import com.example.lifecycle.room.entries.UserEntry;

import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainViewHolder> {

    List<UserEntry> mUserData;

    public MainRecyclerViewAdapter(List<UserEntry> users) {
        mUserData = users;
    }

    public MainRecyclerViewAdapter(){
        this(null);
    }

    @MainThread
    public void setUserData(List<UserEntry> users) {
        mUserData = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_view, parent,false);
        return new MainViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        UserEntry user = mUserData.get(position);
        holder.mNameView.setText(user.name + user.phone);
    }

    @Override
    public int getItemCount() {
        if (mUserData != null) {
            return mUserData.size();
        }

        return 0;
    }
}

class MainViewHolder extends RecyclerView.ViewHolder {

    TextView mNameView;

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);
        mNameView = itemView.findViewById(R.id.item_name);
    }
}
