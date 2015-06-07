package com.darkjaguar.dj_decor.header.interfaces;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface DJHeaderDecorAdapter<ViewHolder extends RecyclerView.ViewHolder> {
    long getHeaderId(int position);
    ViewHolder onCreateHeaderViewHolder(ViewGroup parent);
    void onBindHeaderViewHolder(ViewHolder viewHolder, int postion);
    int getItemCount();
}
