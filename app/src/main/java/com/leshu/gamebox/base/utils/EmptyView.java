package com.leshu.gamebox.base.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jiutong.base.utils.StringUtils;
import com.leshu.gamebox.R;
import com.leshu.gamebox.base.MyApplication;


/**
 * 设置
 */

public class EmptyView {


    public View subView;
    public View view;
    public Context context;
    public Drawable res;


    public void setTitle(String title) {
        this.title = title;
    }

    public void setRes(Drawable res) {
        this.res = res;
    }

    public String title;

    public EmptyView(Context context, View subView) {
        this.subView = subView;
        this.context = context;
    }


    /**
     * 设置listview添加空的数据界面
     *
     * @return
     */
    public static View getEmptyView(String message) {
        View view = LayoutInflater.from(MyApplication.Companion.getInstance()).inflate(R.layout.layout_none_emptyview, null);
        TextView emptyView = (TextView) view.findViewById(R.id.tv_layout_none_title);
        if (!StringUtils.INSTANCE.isEmpty(message)) {
            emptyView.setText(message);
        }
        return view;
    }

}
