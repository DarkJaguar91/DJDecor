package com.darkjaguar.dj_decor.header.interfaces;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public interface DJHeaderProvider {
    /**
     * Gets the view for the given position in the data set if it exists, or creates one
     * @param position of the item in the data set
     * @param parent for the given view if it needs to be created
     * @return The {@link View} for the given position in the data set
     */
    View getView(int position, RecyclerView parent);

    RecyclerView.ViewHolder createFloatingView(RelativeLayout parent);
}
