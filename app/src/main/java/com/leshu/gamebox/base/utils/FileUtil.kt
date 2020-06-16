package com.jiutong.base.utils

import android.os.Environment
import com.leshu.gamebox.base.MyApplication
import java.io.File
import java.io.IOException


/**
 *
 * 文件操作工具类
 */

object FileUtil {
    /**
     * 当SD已挂载或者SD为内置存储不能卸载时将文件缓存在SD/Android/data/<application package>/cache目录下,
     * 否则缓存在data/data/<application package>/cache目录下
     *
     * @return
    </application></application> */
    val fitCacheDir: String
        get() {
            val cachePath: String
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
                cachePath = MyApplication.getInstance().externalCacheDir!!.path
            } else {
                cachePath = cacheDir
            }
            return cachePath
        }

    /**
     * 获取缓存目录
     * data/data/<application package>/cache
     *
     * @return
    </application> */
    val cacheDir: String
        get() = MyApplication.getInstance().cacheDir.path

    /**
     * 图片缓存目录
     *
     * @return
    </application> */
    val picCacheDir: String
        get() {
            val path = "$fitCacheDir/pic"
            try {
                val dir = File(path)
                if (!dir.exists() || !dir.isDirectory) {
                    dir.mkdir()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return path
        }

    /**
     * 清空图片缓存目录
     */
    fun clearPicCacheDir() {
        try {
            val dir = File(picCacheDir)
            if (dir.exists() && dir.isDirectory) {
                dir.listFiles().forEach { it.delete() }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * app更新缓存目录
     */
    val apkUpdateCacheDir: String
        get() {
            val path = "$fitCacheDir/apk"
            try {
                val dir = File(path)
                if (!dir.exists() || !dir.isDirectory) {
                    dir.mkdir()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return path
        }
}
