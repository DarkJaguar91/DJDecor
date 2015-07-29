package com.darkjaguar.dj_decor.header;

import android.graphics.Point;
import android.graphics.Rect;
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
    public boolean isFirstView(View view, ViewGroup parent) {
        return view.getTop() <= DJMarginCalculator.getMarginsForView(view).top;
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
    public Point getPositionForHeader(View view, View header, ViewGroup parent, int position, boolean isFirstView) {
        Rect marginsForView = DJMarginCalculator.getMarginsForView(header);
        int x, y;
        x = marginsForView.left;
        y = Math.max(marginsForView.top, view.getTop() - header.getHeight() - marginsForView.bottom);

        if (isFirstView && (position == adapter.getItemCount() || needsNewHeader(position + 1))) {
            y = Math.min(y, view.getBottom() - header.getHeight());
        }

        return new Point(x, y);
    }
}
