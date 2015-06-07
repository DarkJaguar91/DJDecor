package com.darkjaguar.dj_decor.header.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class DJRecyclerViewOrientationHelper {
    public static int getRecyclerViewOrientation(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation();
        }
        throw new RecyclerViewLayoutNotSupportedException();
    }

    public static boolean isRecyclerViewReversed(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) recyclerView.getLayoutManager()).getReverseLayout();
        }
        throw new RecyclerViewLayoutNotSupportedException();
    }

    private static class RecyclerViewLayoutNotSupportedException extends RuntimeException {
        public RecyclerViewLayoutNotSupportedException() {
            super("DJHeaderDecor currently only supports LinearLayouts!");
        }
    }
}
