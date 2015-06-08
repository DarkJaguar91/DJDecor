package com.darkjaguar.dj_decor.header.interfaces;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface DJHeaderDecorAdapter<ViewHolder extends RecyclerView.ViewHolder> {
    /**
     * Should return an ID associated to the header for the recycler view item at the given position.
     *  Every item with the same ID will be grouped and only 1 header will be shown for that section.
     *  (Items with the same ID split by an item with with a different ID will cause 2 of the same
     *   headers to appear)
     * @param position of the element in the recycler view
     * @return The ID for the header associated with the item at position
     */
    long getHeaderId(int position);

    /**
     * Should create the ViewHolder used by all header items.
     * @param parent view to be used as the Header ViewHolder parent
     * @return The ViewHolder used for header Items
     */
    ViewHolder onCreateHeaderViewHolder(ViewGroup parent);

    /**
     * Should bind all data associated with the Header ViewHolder for recycler view item at the
     *  given position
     * @param viewHolder to bind the data to
     * @param position of recycler view item associated with the Header ViewHolder
     */
    void onBindHeaderViewHolder(ViewHolder viewHolder, int position);

    /**
     * @see {@link RecyclerView.Adapter#getItemCount()}
     */
    int getItemCount();
}
