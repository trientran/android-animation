package com.trien.dnanimation;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Troy on 26-Mar-18.
 */

class MyViewPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private int[] layouts;

    public MyViewPagerAdapter(Context context, int[] layouts) {

        this.context = context;
        this.layouts = layouts;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = null;
        if (layoutInflater != null) {
            view = layoutInflater.inflate(layouts[position], container, false);
        }
        container.addView(view);
        Log.d("trienViewid", String.valueOf(view != null ? view.getId() : 0) + " " + layouts[position]);
        return view;
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        View view = (View) object;
        container.removeView(view);
    }
}
