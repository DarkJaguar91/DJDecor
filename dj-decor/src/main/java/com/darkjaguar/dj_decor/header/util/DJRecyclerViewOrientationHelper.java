package com.darkjaguar.dj_decor.header.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class DJRecyclerViewOrientationHelper {
    /**
     * A Util to get the orientation of the layout associated with the given {@link RecyclerView}
     * @param recyclerView to get the orientation for
     * @return the {@link Integer} representation of the {@link RecyclerView}'s layout manager
     */
    public static int getRecyclerViewOrientation(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation();
        }
        throw new RecyclerViewLayoutNotSupportedException();
    }

    /**
     * A Util to determine if the layout manager for the given {@link RecyclerView} is reversed
     * @param recyclerView to get the direction of
     * @return True if the layout manager for the {@link RecyclerView} is reversed
     */
    public static boolean isRecyclerViewReversed(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) recyclerView.getLayoutManager()).getReverseLayout();
        }
        throw new RecyclerViewLayoutNotSupportedException();
    }

    /**
     * Exception used if the given {@link RecyclerView} has a {@link android.support.v7.widget.RecyclerView.LayoutManager}
     *  that is not supported by this library.
     */
    private static class RecyclerViewLayoutNotSupportedException extends RuntimeException {
        public RecyclerViewLayoutNotSupportedException() {
            super("DJHeaderDecor currently only supports LinearLayouts!");
        }
    }
}
