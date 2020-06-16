package com.jiutong.base.utils

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout

object ViewGroupUtils {
    fun setLinearLayoutlayout(layout: LinearLayout, height: Int?, width: Int?) {
        val lp: ViewGroup.LayoutParams = layout.layoutParams
        if (height == null) lp.height = ViewGroup.LayoutParams.MATCH_PARENT else lp.height = height

        if (width == null) lp.width = ViewGroup.LayoutParams.MATCH_PARENT else lp.width = width

        layout.layoutParams = lp
    }

    fun setRelativeLayoutlayout(layout: RelativeLayout, height: Int?, width: Int?) {
        val lp: ViewGroup.LayoutParams = layout.layoutParams
        if (height == null) lp.height = ViewGroup.LayoutParams.MATCH_PARENT else lp.height = height

        if (width == null) lp.width = ViewGroup.LayoutParams.MATCH_PARENT else lp.width = width

        layout.layoutParams = lp
    }
}