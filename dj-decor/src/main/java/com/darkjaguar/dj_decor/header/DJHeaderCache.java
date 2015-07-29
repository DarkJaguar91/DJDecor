package com.darkjaguar.dj_decor.header;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.darkjaguar.dj_decor.header.interfaces.DJHeaderDecorAdapter;
import com.darkjaguar.dj_decor.header.interfaces.DJHeaderProvider;
import com.darkjaguar.dj_decor.header.util.DJMarginCalculator;
import com.darkjaguar.dj_decor.header.util.DJRecyclerViewOrientationHelper;


public class DJHeaderCache implements DJHeaderProvider {
    LruCache<Long, View> headerCache;
    DJHeaderDecorAdapter adapter;
    RecyclerView.ViewHolder floatingView;

    public DJHeaderCache(DJHeaderDecorAdapter adapter) {
        this(adapter, 6);
    }

    public DJHeaderCache(DJHeaderDecorAdapter adapter, int maxSize) {
        this.adapter = adapter;
        headerCache = new LruCache<>(maxSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(int position, RecyclerView parent) {
        long id = adapter.getHeaderId(position);

        View view = headerCache.get(id);

        if (view == null) {
            RecyclerView.ViewHolder viewHolder = adapter.onCreateHeaderViewHolder(parent);
            view = viewHolder.itemView;

            adapter.onBindHeaderViewHolder(viewHolder, position);
            correctViewSizes(view, parent);

            headerCache.put(id, view);
        }

        return view;
    }

    @Override
    public RecyclerView.ViewHolder createFloatingView(FrameLayout parent) {
        if (floatingView == null) {
            floatingView = adapter.onCreateHeaderViewHolder(parent);

            ViewGroup.LayoutParams params = floatingView.itemView.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            floatingView.itemView.setLayoutParams(params);

            parent.addView(floatingView.itemView);
        }
        return floatingView;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        this.headerCache.evictAll();
    }

    /**
     * Method to generate the layout for the given view.
     *  This method is needed for views that do not already have a given width and height and use
     *  match_parent and wrap_content. If this is not called for those view types nothing will be drawn.
     * @param view to correct the layout for.
     * @param parent view for the view that needs its layout correct
     */
    private void correctViewSizes(View view, RecyclerView parent) {
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        Rect margins = DJMarginCalculator.getMarginsForView(view);

        int widthSpec;
        int heightSpec;

        int orientation = DJRecyclerViewOrientationHelper.getRecyclerViewOrientation(parent);

        widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), orientation == RecyclerView.VERTICAL ? View.MeasureSpec.EXACTLY : View.MeasureSpec.UNSPECIFIED);
        heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), orientation == RecyclerView.VERTICAL ? View.MeasureSpec.UNSPECIFIED : View.MeasureSpec.EXACTLY);

        int childWidth = ViewGroup
                .getChildMeasureSpec(widthSpec, parent.getPaddingLeft() + parent.getPaddingRight() + margins.left + margins.right,
                                     view.getLayoutParams().width);
        int childHeight = ViewGroup
                .getChildMeasureSpec(heightSpec, parent.getPaddingTop() + parent.getPaddingBottom(),
                                     view.getLayoutParams().height);
        view.measure(childWidth, childHeight);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }
}
