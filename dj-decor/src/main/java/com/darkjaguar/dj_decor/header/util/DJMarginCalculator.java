package com.darkjaguar.dj_decor.header.util;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

public class DJMarginCalculator {
    /**
     * A simple util to generate a {@link Rect} of margins for the given {@link View}
     * @param view for which to get the margins
     * @return a {@link Rect} with the margins for the given {@link View}
     */
    public static Rect getMarginsForView(View view) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            return new Rect(
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).leftMargin,
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin,
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).rightMargin,
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin
            );
        }
        return new Rect(0, 0, 0, 0);
    }
}
