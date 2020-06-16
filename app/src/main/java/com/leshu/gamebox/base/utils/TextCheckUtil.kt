package com.jiutong.base.utils

import android.text.InputFilter


object TextCheckUtil {
    /**
     * 只能输入英文字符和数字
     */
    val letterOrDigitFilter: InputFilter = InputFilter { source, start, end, _, _, _ ->
        (start until end)
                .filterNot {
                    val ascii = source[it].toInt()
                    //0-9   A-Z   a-z
                    (ascii in 48..57) || (ascii in 65..90) || (ascii in 97..122)
                }
                .forEach { return@InputFilter "" }
        null
    }

    /**
     * 手机号验证
     *
     *    中国电信号段     133、149、153、173、177、180、181、189、199
     *    中国联通号段    130、131、132、145、155、156、166、175、176、185、186
     *    中国移动号段    134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
     */
    fun checkMobilePhone(mobilePhone: String): Boolean {
        val pattern = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}\$"
        return mobilePhone.matches(Regex(pattern = pattern))
    }

    /**
     * 19位全国正式唯一学籍号验证
     */
    fun checkRollCode(rollCode: String): Boolean {
        val pattern = "^[A-Z]\\d{6}(18|19|20)?\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])\$"
        return rollCode.matches(Regex(pattern = pattern))
    }

    /**
     * 18位身份证验证
     */
    fun checkIdentityCard(identityCard: String): Boolean {
        val pattern = "^\\d{6}(18|19|20)?\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])\$"
        return identityCard.matches(Regex(pattern = pattern))
    }
}