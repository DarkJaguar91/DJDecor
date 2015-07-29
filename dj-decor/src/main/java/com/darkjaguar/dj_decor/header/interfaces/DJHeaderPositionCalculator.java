package com.darkjaguar.dj_decor.header.interfaces;

import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;

public interface DJHeaderPositionCalculator {
    /**
     * Determines if the given view is the first view (closest to the "top" of the recycler view) for
     *  any given layout orientation/direction.
     * @param orientation of the parent view Horizontal|Vertical
     * @param isReversed True if the layouts direction is reversed
     * @param view which is being tested in the parent view
     * @param parent view that holds the view
     * @return If the view is is the first view in the parent
     */
    boolean isFirstView(View view, ViewGroup parent);

    /**
     * Determines if the view at the given position should have a different header than the previous view
     * @param position of the item in the recycler view
     * @return True if the position needs a new header
     */
    boolean needsNewHeader(int position);

    /**
     * Determines the X and Y offsets for the given header view which is associated to the given View
     *  If its the isFirstView it will be placed at the top of the parent view unless being "pushed" off
     *  screen by another header.
     * @param orientation of the parent view Horizontal|Vertical
     * @param isReversed True if the views direction is reversed
     * @param view associated with the given header
     * @param header view to draw
     * @param parent view containing the view and header
     * @param position in the recycler views data set for the given view
     * @param isFirstView if the view given is the first item in the parent vuew
     * @return The X and Y offsets for the header in a {@link Point}
     */
    Point getPositionForHeader(View view, View header, ViewGroup parent, int position, boolean isFirstView);
}
