package com.darkjaguar.dj_decor.header;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.darkjaguar.dj_decor.header.interfaces.DJHeaderDecorAdapter;
import com.darkjaguar.dj_decor.header.interfaces.DJHeaderPositionCalculator;
import com.darkjaguar.dj_decor.header.interfaces.DJHeaderProvider;
import com.darkjaguar.dj_decor.header.util.DJMarginCalculator;
import com.darkjaguar.dj_decor.header.util.DJRecyclerViewOrientationHelper;

public class DJHeaderDecor extends RecyclerView.ItemDecoration {
    protected final DJHeaderDecorAdapter adapter;
    protected final DJHeaderProvider headerCache;
    protected final DJHeaderPositionCalculator positionCalculator;
    protected final DJDecorRecyclerView mainView;

    public DJHeaderDecor(DJHeaderDecorAdapter adapter, DJDecorRecyclerView mainView) {
        this.adapter = adapter;
        headerCache = new DJHeaderCache(adapter);
        positionCalculator = new DJHeaderPositionCalculatorImpl(adapter);
        this.mainView = mainView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);

        if (position == RecyclerView.NO_POSITION) return;

        View header = headerCache.getView(position, parent);
        if (positionCalculator.needsNewHeader(position)) {
            int orientation = DJRecyclerViewOrientationHelper.getRecyclerViewOrientation(parent);
            boolean reversed = DJRecyclerViewOrientationHelper.isRecyclerViewReversed(parent);
            Rect marginsForView = DJMarginCalculator.getMarginsForView(header);

            if (orientation == RecyclerView.VERTICAL) {
                outRect.left = 0;
                int headerHeight = marginsForView.top + marginsForView.bottom + header.getHeight();
                if (reversed) {
                    outRect.bottom = headerHeight;
                } else {
                    outRect.top = headerHeight;
                }
            } else {
                outRect.top = 0;
                int headerWidth = marginsForView.left + marginsForView.right + header.getWidth();
                if (reversed) {
                    outRect.right = headerWidth;
                } else {
                    outRect.left = headerWidth;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        if (parent.getChildCount() <= 0 || adapter.getItemCount() <= 0) return;

        boolean hasAFloater = false;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);

            if (position == RecyclerView.NO_POSITION) continue;

            boolean firstView = positionCalculator.isFirstView(view, parent);
            boolean needsNewHeader = positionCalculator.needsNewHeader(position);
            if (firstView || needsNewHeader) {
                View header = headerCache.getView(position, parent);

                Point headerPos = positionCalculator
                        .getPositionForHeader(view, header, parent, position, firstView);

                if (!movingOverNextHeader(view, header, position) && (firstView  || obscuringHeader(view, header))) {
                    hasAFloater = true;
                    mainView.showHoveringHeader(position);
                } else {
                    c.save(); c.translate(headerPos.x, headerPos.y);
                    header.draw(c);
                    c.restore();
                }
            }
        }
        if (!hasAFloater) {
            mainView.hideHoveringHeader();
        }
    }

    private boolean movingOverNextHeader(View view, View header, int position) {
        if (header.getHeight() + DJMarginCalculator.getMarginsForView(header).bottom > view.getBottom()) {
            if (position == adapter.getItemCount() || positionCalculator.needsNewHeader(position+1)) {
                return true;
            }
        }
        return false;
    }

    private boolean obscuringHeader(View view, View header) {
        return view.getTop() - header.getHeight() < 0;
    }

    /**
     * Method to relay a clear to the cache for this Decor
     */
    public void clearCache() {
        headerCache.clear();
    }
}
