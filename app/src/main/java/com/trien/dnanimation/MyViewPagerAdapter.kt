package com.trien.dnanimation

import android.content.Context
import androidx.viewpager.widget.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Troy on 26-Mar-18.
 */

internal class MyViewPagerAdapter(private val context: Context, private val layouts: IntArray) : PagerAdapter() {

    private var layoutInflater: LayoutInflater? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var view: View? = null
        if (layoutInflater != null) {
            view = layoutInflater!!.inflate(layouts[position], container, false)
        }
        container.addView(view)
        Log.d("trienViewid", (view?.id ?: 0).toString() + " " + layouts[position])
        return view!!
    }

    override fun getCount(): Int {
        return layouts.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        val view = `object` as View
        container.removeView(view)
    }
}
