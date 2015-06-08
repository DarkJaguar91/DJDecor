package com.darkjaguar.dj_decor.header;

import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.darkjaguar.dj_decor.header.interfaces.DJHeaderDecorAdapter;
import com.darkjaguar.dj_decor.header.interfaces.DJHeaderPositionCalculator;
import com.darkjaguar.dj_decor.header.util.DJMarginCalculator;


public class DJHeaderPositionCalculatorImpl implements DJHeaderPositionCalculator {
    protected final DJHeaderDecorAdapter adapter;

    public DJHeaderPositionCalculatorImpl(DJHeaderDecorAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFirstView(View view, ViewGroup parent, int orientation, boolean isReversed) {
        if (orientation == RecyclerView.VERTICAL) {
            if (isReversed) {
                return view.getBottom() >= parent.getHeight() - DJMarginCalculator.getMarginsForView(view).bottom;
            } else {
                return view.getTop() <= DJMarginCalculator.getMarginsForView(view).top;
            }
        } else {
            if (isReversed) {
                return view.getRight() >= parent.getWidth() - DJMarginCalculator.getMarginsForView(view).right;
            } else {
                return view.getLeft() <= DJMarginCalculator.getMarginsForView(view).left;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean needsNewHeader(int position) {
        if (position <= 0) return true;
        long id = adapter.getHeaderId(position);
        long prevId = adapter.getHeaderId(position - 1);

        return !(id == -1 || prevId == -1) && id != prevId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point getPositionForHeader(View view, View header, ViewGroup parent, int position, boolean isFirstView, int orientation, boolean isReversed) {
        Rect marginsForView = DJMarginCalculator.getMarginsForView(header);
        int x, y;
        if (orientation == RecyclerView.VERTICAL) {
            x = marginsForView.left;
            if (isReversed) {
                y = Math.min(parent.getHeight() - header.getHeight() - marginsForView.bottom, view.getBottom() + marginsForView.bottom);

                if (isFirstView && (position == adapter.getItemCount() || needsNewHeader(position + 1))) {
                    y = Math.max(y, view.getTop());
                }
            } else {
                y = Math.max(marginsForView.top, view.getTop() - header.getHeight() - marginsForView.bottom);

                if (isFirstView && (position == adapter.getItemCount() || needsNewHeader(position + 1))) {
                    y = Math.min(y, view.getBottom() - header.getHeight());
                }
            }
        } else {
            y = marginsForView.left;

            if (isReversed) {
                x = Math.min(parent.getWidth() - header.getWidth() - marginsForView.right, view.getRight() + marginsForView.left);

                if (isFirstView && (position == adapter.getItemCount() || needsNewHeader(position + 1))) {
                    x = Math.max(x, view.getLeft());
                }
            } else {
                x = Math.max(marginsForView.left, view.getLeft() - header.getWidth() - marginsForView.right);

                if (isFirstView && (position == adapter.getItemCount() || needsNewHeader(position + 1))) {
                    x = Math.min(x, view.getRight() - header.getWidth());
                }
            }
        }

        return new Point(x, y);
    }
}
