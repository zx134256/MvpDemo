package com.leshu.gamebox.base.utils

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class MyAppGlideModule : AppGlideModule() {
    /**
     * 如果你已经迁移到 Glide v4 的 AppGlideModule 和 LibraryGlideModule ，
     * 你可以完全禁用清单解析。这样可以改善 Glide 的初始启动时间，并避免尝试解析元数据时的一些潜在问题
     */
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}