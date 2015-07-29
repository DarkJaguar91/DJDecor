package com.darkjaguar.dj_decor.header;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.darkjaguar.dj_decor.header.interfaces.DJHeaderDecorAdapter;

public class DJDecorRecyclerView extends FrameLayout {
    DJRecyclerView recyclerView;
    DJHeaderDecor headerDecor;
    DJHeaderDecorAdapter headerAdapter;
    boolean hoveringHeaderVisible = false, animate = false, scrolling = false;
    RecyclerView.ViewHolder floatingHeaderItem;
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

    public void showHoveringHeader(int position) {
        hoveringHeaderVisible = true;
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
            }
            if (animate) {
                animate = false;
                ViewCompat.animate(floatingHeaderItem.itemView).setDuration(300).translationYBy(
                        -floatingHeaderItem.itemView.getHeight())
                          .setListener(new ViewPropertyAnimatorListener() {
                              @Override
                              public void onAnimationStart(View view) {

                              }

                              @Override
                              public void onAnimationEnd(View view) {
                                  floatingHeaderItem.itemView.setAlpha(0.0f);
                                  floatingHeaderItem.itemView.setTranslationY(0);
                              }

                              @Override
                              public void onAnimationCancel(View view) {

                              }
                          }).start();
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
