package com.darkjaguar.dj_decor.header;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.darkjaguar.dj_decor.header.interfaces.DJHeaderDecorAdapter;
import com.darkjaguar.dj_decor.header.interfaces.DJHeaderPositonCalculator;
import com.darkjaguar.dj_decor.header.interfaces.DJHeaderProvider;
import com.darkjaguar.dj_decor.header.util.DJMarginCalculator;

public class DJHeaderDecor extends RecyclerView.ItemDecoration {
    protected final DJHeaderDecorAdapter adapter;
    protected final DJHeaderProvider headerCache;
    protected final DJHeaderPositonCalculator positionCalculator;

    public DJHeaderDecor(DJHeaderDecorAdapter adapter) {
        this.adapter = adapter;
        headerCache = new DJHeaderCache(adapter);
        positionCalculator = new DJHeaderPositionCalculatorImpl(adapter);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);

        if (position == RecyclerView.NO_POSITION) return;

        View header = headerCache.getView(position, parent);
        if (positionCalculator.needsNewHeader(position)) {
            Rect marginsForView = DJMarginCalculator.getMarginsForView(header);
            outRect.left = marginsForView.left;
            outRect.top = marginsForView.top + marginsForView.bottom + header.getHeight();
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        if (parent.getChildCount() <= 0 || adapter.getItemCount() <= 0) return;

        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);

            if (position == RecyclerView.NO_POSITION) continue;

            boolean firstView = positionCalculator.isFirstView(view);
            boolean needsNewHeader = positionCalculator.needsNewHeader(position);
            if (firstView || needsNewHeader) {
                View header = headerCache.getView(position, parent);

                Point headerPos = positionCalculator.getPositionForHeader(view, header, position, firstView);

                c.save();
                c.translate(headerPos.x, headerPos.y);
                header.draw(c);
                c.restore();
            }
        }
    }
}
