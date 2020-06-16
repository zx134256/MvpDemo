package com.jiutong.base.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.leshu.gamebox.R
import java.io.File

/**
 *TODO FIX  why  can not use GlideApp
 * @date: 2018/8/22 19:37
 */
object GlideUtil {
    /**
     * 默认的RequestOptions
     */
    private val defaultOptions by lazy {
        RequestOptions().centerCrop()
                .placeholder(R.mipmap.ic_glide_defact)
                .error(R.mipmap.ic_glide_defact)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    /**
     * 加载本地图片
     */
    fun loadFile(file: File, iv: ImageView, options: RequestOptions? = null, listener: RequestListener<Drawable>? = null) {
        val request = Glide.with(iv.context)
                .load(file)
                .apply(defaultOptions)
        if (options != null) {
            request.apply(options)
        }
        //本地图片无需磁盘缓存
        request.listener(listener).into(iv)
    }

    /**
     *  加载网络图片
     *
     *  apply() 方法可以被调用多次，因此 RequestOption 可以被组合使用。
     *  如果 RequestOptions 对象之间存在相互冲突的设置，那么只有最后一个被应用的 RequestOptions 会生效。
     */
    fun loadUrlOrPath(context: Context, url: String, iv: ImageView, options: RequestOptions? = null, listener: RequestListener<Drawable>? = null) {
        val request = Glide.with(context)
                .load(url)

                .apply(defaultOptions)
        if (options != null) {
            request.apply(options)
        }
        request.listener(listener).into(iv)
    }

    abstract class GlideLoadListener<Drawable> : RequestListener<Drawable> {

        abstract fun onFailed(): Boolean
        abstract fun onSuccess(resource: Drawable): Boolean
        /**
         * 自动装载图片
         * 有时需要获取资源
         */
        override fun onResourceReady(
                resource: Drawable,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
        ): Boolean {
            return onSuccess(resource)
        }

        override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
        ): Boolean {
            return onFailed()
        }
    }
}