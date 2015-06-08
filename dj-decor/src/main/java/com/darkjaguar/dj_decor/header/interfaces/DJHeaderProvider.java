package com.darkjaguar.dj_decor.header.interfaces;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public interface DJHeaderProvider {
    /**
     * Gets the view for the given position in the data set if it exists, or creates one
     * @param position of the item in the data set
     * @param parent for the given view if it needs to be created
     * @return The {@link View} for the given position in the data set
     */
    View getView(int position, RecyclerView parent);

    /**
     * Clears the provider
     *  If items are cached to ensure all items are recalculated
     */
    void clear();
}
