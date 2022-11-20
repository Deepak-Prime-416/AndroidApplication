package com.example.androidapplication.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplication.R;

import java.util.ArrayList;

public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.ViewHolder>{

    private ArrayList<Fragment> fragmentArrayList;

    public FragmentAdapter(ArrayList<Fragment> fragmentArrayList) {
        this.fragmentArrayList = fragmentArrayList;
    }

    @NonNull
    @Override
    public FragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(fragmentArrayList.get(position).getTitle());
        holder.activeTime.setText(String.valueOf(fragmentArrayList.get(position).getActiveTime()));
        holder.restTime.setText(String.valueOf(fragmentArrayList.get(position).getRestTime()));
    }

    public void setFragmentArrayList(Fragment fragment){
        fragmentArrayList.add(fragment);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return fragmentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView activeTime;
        private TextView restTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.FragmentTitle);
            activeTime = itemView.findViewById(R.id.CountTime);
            restTime = itemView.findViewById(R.id.RestTime);
        }
    }
}
