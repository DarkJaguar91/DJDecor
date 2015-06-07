package com.darkjaguar.dj_decor.header;

import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;

import com.darkjaguar.dj_decor.header.interfaces.DJHeaderDecorAdapter;
import com.darkjaguar.dj_decor.header.interfaces.DJHeaderProvider;


public class DJHeaderCache implements DJHeaderProvider {
    LruCache<Long, View> headerCache;
    DJHeaderDecorAdapter adapter;

    public DJHeaderCache(DJHeaderDecorAdapter adapter) {
        this(adapter, 6);
    }

    public DJHeaderCache(DJHeaderDecorAdapter adapter, int maxSize) {
        this.adapter = adapter;
        headerCache = new LruCache<>(maxSize);
    }

    @Override
    public View getView(int position, ViewGroup parent) {
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

    private void correctViewSizes(View view, ViewGroup parent) {
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        int widthSpec;
        int heightSpec;

        widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                parent.getPaddingLeft() + parent.getPaddingRight(), view.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                parent.getPaddingTop() + parent.getPaddingBottom(), view.getLayoutParams().height);
        view.measure(childWidth, childHeight);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }
}
