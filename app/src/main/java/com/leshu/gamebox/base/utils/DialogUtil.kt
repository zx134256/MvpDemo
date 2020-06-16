package com.jiutong.base.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.leshu.gamebox.R

object DialogUtil {

    /**
     * 显示提示对话框，只有确认按钮
     * @param context
     * @param text
     * @param isCancelable
     * @param isCanceledOnTouchOutside
     * @param onNext
     */
    fun showTipsDialog(context: Context, title: String = "", text: String, isCancelable: Boolean = true, isCanceledOnTouchOutside: Boolean = true,
                       positive: ((dialog: DialogInterface) -> Unit)? = null) {
        UIUtil.postMainThread ({
            if (context != null && context is Activity && !context.isFinishing) {
                val builder = AlertDialog.Builder(context)
                if (!title.isNullOrBlank()) {
                    builder.setTitle(title)
                }
                builder.setMessage(text)
                builder.setPositiveButton(R.string.confirm) { dialog, _ ->
                    positive?.invoke(dialog)
                    if (context != null && context is Activity && !context.isFinishing) {
                        dialog.dismiss()
                    }
                }
                val dialog = builder.show()
                dialog.setCancelable(isCancelable)
                dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
            }
        },this)
    }
}