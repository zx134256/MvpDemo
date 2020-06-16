package com.jiutong.base.utils.photoview

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class PhotoViewAdapter(protected val mContext: Context, private val imgUrls: List<String>) : PagerAdapter() {
    protected var mOnItemChangeListener: OnItemChangeListener? = null

    override fun getCount(): Int {
        return imgUrls?.size
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        if (mOnItemChangeListener != null)
            mOnItemChangeListener!!.onItemChange(position)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val iv = PhotoViewWrapper(mContext)
        iv.setUrl(imgUrls[position])
        iv.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)

        container.addView(iv, 0)
        return iv
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    fun setOnItemChangeListener(listener: OnItemChangeListener) {
        mOnItemChangeListener = listener
    }

    interface OnItemChangeListener {
        fun onItemChange(currentPosition: Int)
    }
}
