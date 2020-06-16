package com.jiutong.base.utils.photoview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.jiutong.haofahuo.util.ImageUrlUtils
import com.leshu.gamebox.R
import com.leshu.gamebox.base.utils.photoview.PhotoView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PhotoViewWrapper : RelativeLayout {

    private var loadingDialog: View? = null

    private var imageView: PhotoView? = null

    protected var mContext: Context

    constructor(ctx: Context) : super(ctx) {
        mContext = ctx
        init()
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        mContext = ctx
        init()
    }

    protected fun init() {
        imageView = PhotoView(mContext)
        imageView?.enable()
        val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        imageView?.layoutParams = params
        this.addView(imageView)
        imageView?.visibility = View.GONE

        loadingDialog = LayoutInflater.from(mContext).inflate(R.layout.photo_view_zoom_progress, null)
        loadingDialog?.layoutParams = params
        this.addView(loadingDialog)
    }

    fun setUrl(imageUrl: String) {
//        GlideUtil.load(mContext, ImageUrlUtils.getCompleteUrl(imageUrl, mContext), imageView!!, null, object : RequestListener<Drawable> {
//            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
//                                        loadingDialog?.visibility = View.GONE
//                        imageView?.visibility = View.VISIBLE
//                return false
//            }
//
//            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                                        loadingDialog?.visibility = View.GONE
//                        imageView?.visibility = View.VISIBLE
//                return false
//            }
//        })
        Picasso.with(mContext).load(ImageUrlUtils.getCompleteUrl(imageUrl,mContext))
                .error(R.mipmap.ic_glide_defact)
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        loadingDialog?.visibility = View.GONE
                        imageView?.visibility = View.VISIBLE
                    }

                    override fun onError() {
                        loadingDialog?.visibility = View.GONE
                        imageView?.visibility = View.VISIBLE
                    }
                })
    }
}