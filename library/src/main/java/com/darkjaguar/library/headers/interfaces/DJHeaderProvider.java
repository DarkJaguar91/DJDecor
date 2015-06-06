package com.darkjaguar.library.headers.interfaces;

import android.view.View;
import android.view.ViewGroup;

public interface DJHeaderProvider {
    View getView(int position, ViewGroup parent);
}
