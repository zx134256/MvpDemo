package com.jiutong.base.utils

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import com.leshu.gamebox.base.MyApplication


/**
 *
 *
 * 网络工具类
 */

object NetworkAndGPSUtil {
    /**
     * 判断是否连接网络
     *
     * @return
     */
    val isNetworkConnected: Boolean
        get() {
            val manager = MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = manager?.activeNetworkInfo
            return activeNetworkInfo?.isAvailable ?: false
        }

    /**
     * 获取使用的网络连接类型
     *
     * @return -1 无网络连接
     */
    val connectedType: Int
        get() {
            val manager = MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = manager.activeNetworkInfo
            return if (activeNetworkInfo != null && activeNetworkInfo.isAvailable) {
                activeNetworkInfo.type
            } else -1
        }

    /**
     * 当前使用的网络连接类型是否是Wifi网络
     *
     * @return
     */
    val isWifiConnectedType: Boolean
        get() = connectedType == ConnectivityManager.TYPE_WIFI

    /**
     * 当前使用的网络连接类型是否是移动网络
     *
     * @return
     */
    val isMobileConnectedType: Boolean
        get() = connectedType == ConnectivityManager.TYPE_MOBILE


    /**
     * 判断GPS是否开启
     *
     * @return true 表示开启
     */
    fun isOpenGPS(): Boolean {
        val locationManager = MyApplication.getInstance().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
//        val agps = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//        return gps || agps
        return gps
    }
}
