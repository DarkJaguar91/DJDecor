package com.darkjaguar.dj_decor.header;


import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.darkjaguar.dj_decor.header.interfaces.DJHeaderDecorAdapter;
import com.darkjaguar.dj_decor.header.util.DJMarginCalculator;

public class DJDecorRecyclerView extends FrameLayout {
    DJRecyclerView recyclerView;
    DJHeaderDecor headerDecor;
    DJHeaderDecorAdapter headerAdapter;
    boolean hoveringHeaderVisible = false, animate = false, scrolling = false;
    RecyclerView.ViewHolder floatingHeaderItem;
    float offsetForAnimation = -1;
    private Runnable hideAnimation = hideAnimation = new Runnable() {
        @Override
        public void run() {
            animate = true;
            display();
        }
    };

    public DJDecorRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        recyclerView = new DJRecyclerView(context);
        recyclerView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                  ViewGroup.LayoutParams.MATCH_PARENT));

        this.addView(recyclerView);
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
    }

    protected void display() {
        if (hoveringHeaderVisible) {
            if (scrolling) {
                floatingHeaderItem.itemView.setAlpha(1.0f);
                floatingHeaderItem.itemView.setTranslationY(0);
            }
            if (animate) {
                animate = false;
                ViewCompat.animate(floatingHeaderItem.itemView).setDuration(300).translationY(offsetForAnimation - floatingHeaderItem.itemView
                        .getHeight() - DJMarginCalculator.getMarginsForView(floatingHeaderItem.itemView).bottom - DJMarginCalculator.getMarginsForView(floatingHeaderItem.itemView).top).start();
            }
        } else {
            floatingHeaderItem.itemView.setAlpha(0.0f);
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    class DJRecyclerView extends RecyclerView {
        public DJRecyclerView(Context context) {
            super(context);
        }

        @Override
        public void setAdapter(Adapter adapter) {
            super.setAdapter(adapter);
            if (adapter instanceof DJHeaderDecorAdapter) {
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
                if (state == SCROLL_STATE_IDLE) {
                    scrolling = false;
                    postDelayed(hideAnimation, 1000);
                } else {
                    scrolling = true;
                    animate = false;
                    display();
                }
            }
        }
    }
}
