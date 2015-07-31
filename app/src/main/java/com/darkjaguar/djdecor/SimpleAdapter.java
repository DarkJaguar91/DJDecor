package com.darkjaguar.djdecor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.darkjaguar.dj_decor.header.interfaces.DJHeaderDecorAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> implements DJHeaderDecorAdapter<SimpleAdapter.ViewHolder> {
    List<String> list;

    public SimpleAdapter() {
        list = new ArrayList<>();

//        for (int i = 0; i < 10000; i++) {
//            list.add(i + "");
//        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(list.get(position));
    }

    @Override
    public long getHeaderId(int position) {
        return position / 10;
    }

    @Override
    public ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolder viewHolder, int postion) {
        viewHolder.text.setText(getHeaderId(postion) + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.text)
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
