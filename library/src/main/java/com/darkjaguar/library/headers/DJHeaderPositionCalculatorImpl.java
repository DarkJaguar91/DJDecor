package com.darkjaguar.library.headers;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

import com.darkjaguar.library.headers.interfaces.DJHeaderDecorAdapter;
import com.darkjaguar.library.headers.interfaces.DJHeaderPositonCalculator;
import com.darkjaguar.library.headers.util.DJMarginCalculator;


public class DJHeaderPositionCalculatorImpl implements DJHeaderPositonCalculator {
    protected final DJHeaderDecorAdapter adapter;

    public DJHeaderPositionCalculatorImpl(DJHeaderDecorAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean isFirstView(View view) {
        return view.getTop() <= DJMarginCalculator.getMarginsForView(view).top;
    }

    @Override
    public boolean needsNewHeader(int position) {
        if (position <= 0) return true;
        long id = adapter.getHeaderId(position);
        long prevId = adapter.getHeaderId(position - 1);

        return !(id == -1 || prevId == -1) && id != prevId;
    }

    @Override
    public Point getPositionForHeader(View view, View header, int position, boolean firstView) {
        Rect marginsForView = DJMarginCalculator.getMarginsForView(header);
        int x = marginsForView.left;
        int y = Math.max(marginsForView.top, view.getTop() - header.getHeight() - marginsForView.bottom);

        if (firstView && (position == adapter.getItemCount() || needsNewHeader(position + 1))) {
            y = Math.min(y, view.getBottom() - header.getHeight());
        }

        return new Point(x, y);
    }
}
