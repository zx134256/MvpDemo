package com.jiutong.base.constant

/**
 * 网络常量
 */
object HttpConstant {
    /**
     * http配置参数
     */
    object Config {
        /**
         * 连接超时
         */
        val CONNECT_TIME_OUT = 30L
        /**
         * 读取超时
         */
        val READ_TIME_OUT = 30L
        /**
         * 写入超时间
         */
        val WRITE_TIME_OUT = 30L
        /**
         * 缓存文件目录
         */
        val CACHE_DIRECTORY = "okHttp_cache"
        /**
         * 缓存大小
         */
        val CACHE_MAX_SIZE = 100 * 1024 * 1024L
    }

}