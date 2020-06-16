package com.jiutong.base.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * @author: xiangyun_liu
 *
 * @date: 2018/12/17 19:11
 */
object InputFilterUtil {

    /**
     * 设置保留小数点后两位
     */
    fun setPricePoint(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var s = s
                if (s.toString().contains(".")) {
                    if (s.length - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3)
                        editText.setText(s)
                        editText.setSelection(s.length)
                    }
                }
                if (s.toString().trim { it <= ' ' }.substring(0) == ".") {
                    s = "0$s"
                    editText.setText(s)
                    editText.setSelection(2)
                }

                if (s.toString().startsWith("0") && s.toString().trim { it <= ' ' }.length > 1) {
                    if (s.toString().substring(1, 2) != ".") {
                        editText.setText(s.subSequence(0, 1))
                        editText.setSelection(1)
                        return
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {}

        })

    }
}