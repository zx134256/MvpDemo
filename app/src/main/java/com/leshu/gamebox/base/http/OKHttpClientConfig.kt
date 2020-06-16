package com.leshu.gamebox.base.http

import com.jiutong.base.constant.HttpConstant
import com.leshu.gamebox.BuildConfig
import com.leshu.gamebox.base.http.interceptor.PublicHeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

/**
 * 配置OkHttpClient
 *
 */

object OKHttpClientConfig {
    val mOkHttpClient by lazy { initConfig() }

    private fun initConfig(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(HttpConstant.Config.CONNECT_TIME_OUT, TimeUnit.SECONDS)
        builder.readTimeout(HttpConstant.Config.READ_TIME_OUT, TimeUnit.SECONDS)
        builder.writeTimeout(HttpConstant.Config.WRITE_TIME_OUT, TimeUnit.SECONDS)
        //错误重连
        builder.retryOnConnectionFailure(true)
        //Url添加公共参数
//        builder.addInterceptor(PublicParamsInterceptor())
        builder.addInterceptor(PublicHeaderInterceptor())
        //debug模式时打印请求日志信息
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)
        }
        //添加缓存，设置缓存文件目录，及大小
//        val cache = Cache(File(FileUtil.getCacheDir(), HttpConstant.CACHE_DIRECTORY), HttpConstant.CACHE_MAX_SIZE)
//        builder.cache(cache)
        //添加缓存拦截器用来配置缓存策略
//        builder.addInterceptor(CacheInterceptor())
//        builder.addNetworkInterceptor(CacheInterceptor())

        //OKHttp初始化设置Https需要的证书
//        if (BuildConfig.API_ADDRESS.startsWith("https://")) {
//            setCertificates(builder, HaoFaHuoApplication.getInstance().assets.open("release.crt"))
//        }

        return builder.build()
    }

    /**
     * 通过okhttpClient来设置证书
     * @param clientBuilder OKhttpClient.builder
     * @param certificates 读取证书的InputStream
     */
    private fun setCertificates(clientBuilder: OkHttpClient.Builder, vararg certificates: InputStream) {
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null)
            for ((index, certificate) in certificates.withIndex()) {
                val certificateAlias = Integer.toString(index)
                keyStore.setCertificateEntry(certificateAlias, certificateFactory
                        .generateCertificate(certificate))
                try {
                    certificate?.close()
                } catch (e: IOException) {
                }
            }
            val trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            val trustManagers = trustManagerFactory.trustManagers
            if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
                throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
            }
            val trustManager = trustManagers[0] as X509TrustManager
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            clientBuilder.sslSocketFactory(sslSocketFactory, trustManager)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
