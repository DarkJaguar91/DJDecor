package com.darkjaguar.dj_decor.header;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.darkjaguar.dj_decor.R;
import com.darkjaguar.dj_decor.header.interfaces.DJHeaderDecorAdapter;
import com.darkjaguar.dj_decor.header.util.DJMarginCalculator;

public class DJDecorRecyclerView extends RelativeLayout {
    DJRecyclerView recyclerView;
    DJHeaderDecor headerDecor;
    DJHeaderDecorAdapter headerAdapter;
    boolean hoveringHeaderVisible = false, animate = false, scrolling = false;
    RecyclerView.ViewHolder floatingHeaderItem;
    SwipeRefreshLayout refreshLayout;
    float offsetForAnimation = -1;

    long hideDuration = 300;
    boolean hideFloatingView = true;

    private Runnable hideAnimation = hideAnimation = new Runnable() {
        @Override
        public void run() {
            animate = true;
            display();
        }
    };
    private int hideDelay = 1000;

    public DJDecorRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DJDecorRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attributeTypes = context.obtainStyledAttributes(attrs, R.styleable.DJDecorRecyclerAttributes);

        for (int i = 0; i < attributeTypes.getIndexCount(); ++i) {
            int attribute = attributeTypes.getIndex(i);
            if (attribute == R.styleable.DJDecorRecyclerAttributes_swipe_refresh) {
                boolean swipeRefresh = attributeTypes.getBoolean(attribute, false);
                if (swipeRefresh) {
                    refreshLayout = new SwipeRefreshLayout(context);
                    refreshLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                               ViewGroup.LayoutParams.MATCH_PARENT));
                    this.addView(refreshLayout);
                }
            }
        }
        attributeTypes.recycle();


        recyclerView = new DJRecyclerView(context, attrs, defStyleAttr);
        recyclerView.setId(NO_ID);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                   ViewGroup.LayoutParams.MATCH_PARENT);
        recyclerView.setLayoutParams(params);

        if (refreshLayout != null) {
            refreshLayout.addView(recyclerView);
        } else {
            this.addView(recyclerView);
        }

        recyclerView.postDelayed(hideAnimation, hideDelay);
    }

    public void showHoveringHeader(int position, float offset) {
        hoveringHeaderVisible = true;
        offsetForAnimation = offset;
        headerAdapter.onBindHeaderViewHolder(floatingHeaderItem, position);
        display();
    }

    public void hideHoveringHeader() {
        hoveringHeaderVisible = false;
        display();
    }

    protected void createFloatingHeader() {
        floatingHeaderItem = headerDecor.headerCache.createFloatingView(this);
        if (headerAdapter.getItemCount() > 0 && hoveringHeaderVisible) {
            floatingHeaderItem.itemView.setAlpha(1.0f);
            recyclerView.postDelayed(hideAnimation, 1000);
        } else {
            floatingHeaderItem.itemView.setAlpha(0.0f);
        }
    }

    protected void display() {
        if (hoveringHeaderVisible) {
            if (scrolling) {
                floatingHeaderItem.itemView.setAlpha(1.0f);
                floatingHeaderItem.itemView.setTranslationY(0);
            }
            if (animate) {
                animate = false;
                ViewCompat.animate(floatingHeaderItem.itemView).setDuration(hideDuration).translationY(offsetForAnimation - floatingHeaderItem.itemView
                        .getHeight() - DJMarginCalculator.getMarginsForView(floatingHeaderItem.itemView).bottom - DJMarginCalculator.getMarginsForView(floatingHeaderItem.itemView).top).start();
            }
        } else {
            floatingHeaderItem.itemView.setAlpha(0.0f);
        }
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return refreshLayout;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public long getHideDuration() {
        return hideDuration;
    }

    public void setHideDuration(long hideDuration) {
        this.hideDuration = hideDuration;
    }

    public boolean isHideFloatingView() {
        return hideFloatingView;
    }

    public void setHideFloatingView(boolean hideFloatingView) {
        this.hideFloatingView = hideFloatingView;
    }

    public int getHideDelay() {
        return hideDelay;
    }

    public void setHideDelay(int hideDelay) {
        this.hideDelay = hideDelay;
    }

    class DJRecyclerView extends RecyclerView {
        public DJRecyclerView(Context context) {
            super(context);
        }

        public DJRecyclerView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public DJRecyclerView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public void setAdapter(Adapter adapter) {
            if (adapter == this.getAdapter()) return;
            super.setAdapter(adapter);
            if (adapter instanceof DJHeaderDecorAdapter) {
                if (headerDecor != null) {
                    this.removeItemDecoration(headerDecor);
                }
                headerAdapter = (DJHeaderDecorAdapter) adapter;
                headerDecor = new DJHeaderDecor(headerAdapter, DJDecorRecyclerView.this);
                this.addItemDecoration(headerDecor);
                createFloatingHeader();
            }
        }

        @Override
        public void onScrollStateChanged(int state) {
            super.onScrollStateChanged(state);
            if (floatingHeaderItem != null) {
                removeCallbacks(hideAnimation);
                if (state == SCROLL_STATE_IDLE && hideFloatingView) {
                    scrolling = false;
                    postDelayed(hideAnimation, hideDelay);
                } else {
                    scrolling = true;
                    animate = false;
                    display();
                }
            }
        }
    }
}
