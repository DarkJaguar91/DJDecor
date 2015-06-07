package com.darkjaguar.dj_decor.header.interfaces;

import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;

public interface DJHeaderPositonCalculator {
    boolean isFirstView(View view, ViewGroup parent, int orientation, boolean reversed);
    boolean needsNewHeader(int position);

    Point getPositionForHeader(View view, View header, ViewGroup parent, int position, boolean firstView, int orientation, boolean reversed);
}
