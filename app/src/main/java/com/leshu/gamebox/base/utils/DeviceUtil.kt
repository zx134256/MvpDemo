package com.jiutong.base.utils

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.jiutong.haofahuo.util.UserFileUtil
import com.leshu.gamebox.base.utils.MD5Util
import java.util.*

/**
 * 设备相关类
 */
object DeviceUtil {
    /**
     * 获取Build的部分信息,这里选用了几个不会随系统更新而改变的值
     *
     *  设备基板名称
     *  设备品牌名称
     *  设备指令集名称
     *  设备驱动名称
     *  手机的型号
     *  整个产品的名称
     *  设备版本号
     *  设备制造商
     */
    private val buildInfo = "/${Build.BOARD}/${Build.CPU_ABI}/${Build.DEVICE}/${Build.MODEL}/${Build.PRODUCT}/${Build.ID}/${Build.MANUFACTURER}"

    /**
     * 获取设备ID
     *
     * @param context
     * @return
     */
    fun getDeviceId(context: Context): String {
        var deviceId = UserFileUtil.readDeviceId(context)
        if (deviceId.isNullOrBlank()) {
            var androidId = getAndroidId(context)
            //如果androidId有问题,重新随机生成
            if (androidId.isNullOrBlank() || "9774d56d682e549c" == androidId || "0123456789abcdef" == androidId
                    || "0000000000000000" == androidId) {
                androidId = UUID.randomUUID().toString()
            }
            //利用androidId和硬件相关信息生成deviceId
            deviceId = UUID(androidId.hashCode().toLong(), buildInfo.hashCode().toLong()).toString()
            UserFileUtil.saveDeviceId(context, deviceId)
        }
        return deviceId
    }

    /**
     * 获取Android Id
     *
     * @param context
     * @return
     */
    fun getAndroidId(context: Context?): String? {
        var str: String? = null
        if (context != null) {
            try {
                str = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return str
    }

    fun getMachineCode(context: Context): String {
        return MD5Util.encrypt(getAndroidId(context) + Build.SERIAL + "6489r3r4h=-.gw").substring(12)//test
    }
}
