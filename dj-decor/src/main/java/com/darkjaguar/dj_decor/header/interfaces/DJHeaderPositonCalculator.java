package com.darkjaguar.dj_decor.header.interfaces;

import android.graphics.Point;
import android.view.View;

public interface DJHeaderPositonCalculator {
    boolean isFirstView(View view);
    boolean needsNewHeader(int position);

    Point getPositionForHeader(View view, View header, int position, boolean firstView);
}
