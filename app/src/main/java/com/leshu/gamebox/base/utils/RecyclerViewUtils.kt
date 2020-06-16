package com.jiutong.base.utils

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.leshu.gamebox.R
import com.leshu.gamebox.base.MyApplication


/**
 *
 * @Description: RecyclerView 类型的封装
 */
object RecyclerViewUtils {
    fun initRecyclerView(mRecyclerView: RecyclerView, adapter: BaseQuickAdapter<*, *>) {
        mRecyclerView.layoutManager = LinearLayoutManager(MyApplication.getInstance())
        mRecyclerView.adapter = adapter
        adapter.setEmptyView(R.layout.layout_none_emptyview, mRecyclerView.parent as ViewGroup)
    }
}