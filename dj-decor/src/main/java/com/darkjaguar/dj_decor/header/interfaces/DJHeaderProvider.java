package com.darkjaguar.dj_decor.header.interfaces;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public interface DJHeaderProvider {
    View getView(int position, RecyclerView parent);
}
