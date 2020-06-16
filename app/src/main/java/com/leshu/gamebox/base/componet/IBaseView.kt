package com.leshu.gamebox.base.componet

interface IBaseView {

    /**
     * 显示加载对话框
     * @param d   关联的订阅，传入非null参数，对话框关闭的时候，取消关联的订阅事件
     * @param text 对话框显示的文本
     * @param cancelable 按返回键是否能关闭对话框
     */
    fun showLoading(text: String = "", cancelable: Boolean = true)


    /**
     * 关闭加载对话框
     */
    fun dismissLoading()
}