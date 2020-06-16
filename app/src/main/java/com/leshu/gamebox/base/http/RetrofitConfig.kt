package com.leshu.gamebox.base.http

import com.jiutong.base.utils.GsonUtil
import com.leshu.gamebox.BuildConfig
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

/**
 *
 *
 * 配置Retrofit
 */

object RetrofitConfig {
    var mRetrofit: Retrofit

    init {
        mRetrofit = initConfig(ApiService.API_ADDRESS)
    }

    fun initConfig(apiAddress: String): Retrofit {
        val builder = Retrofit.Builder()
        /**
         * 配置Retrofit
         * 1.baseUrl() 设置接口请求地址;
         * 2.addConverterFactory() 默认情况下Retrofit接口只能返回Call<ResponseBody>(Call<T>)类型的数据，无法更改T,只有
         * 添加converter后才能将Call<T>中的T指定成自己需要的类型，这里使用GsonConverterFactory将ResponseBody转换成我们
         * 想要的类型;
         * 3.addCallAdapterFactory() 默认情况下Retrofit接口只能返回Call<T>类型的数据，无法更改Call,只有添加CallAdapter后，
         * 才能获得我们想要的类型，这里添加RxJava2CallAdapterFactory后可以获得Observable<T>类型，方便使用RxJava;
         * 4.client() 设置自定义的OkHttpClient,以前的Retrofit版本中不同的Retrofit对象共用同OkHttpClient,在2.0各对象各自持
         * 有不同的OkHttpClient实例，所以当你需要共用OkHttpClient或需要自定义时则可以使用该方法；
         * 5.validateEagerly() 是否在调用create(Class)时检测接口定义是否正确，而不是在调用方法才检测，适合在开发、测试时使用
        </T></T></T></T></ResponseBody> */
        builder.baseUrl(apiAddress)
        // 如是有Gson这类的Converter一定要放在其前面,addConverterFactory是有先后顺序的，如果有多个ConverterFactory都支持同
        // 一种类型，那么就是只有第一个才会被使用，而GsonConverterFactory是不判断是否支持的，所以这里交换了顺序还会有一个
        // 异常抛出，原因是类型不匹配
        builder.addConverterFactory(StringConverterFactory.create())
        builder.addConverterFactory(GsonConverterFactory.create(GsonUtil.mGson))
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        builder.client(OKHttpClientConfig.mOkHttpClient)
        builder.validateEagerly(BuildConfig.DEBUG)
        return builder.build()
    }

    /**
     * 自定义转换器将ResponseBody转换为String
     */
    private class StringConverter : Converter<ResponseBody, String> {

        @Throws(IOException::class)
        override fun convert(value: ResponseBody): String {
            return value.string()
        }

        companion object {
            val INSTANCE = StringConverter()
        }
    }

    /**
     * 创建一个ConverterFactory
     */
    private class StringConverterFactory : Converter.Factory() {

        // 我们只关实现从ResponseBody 到 String 的转换，所以其它方法可不覆盖
        override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
            return if (type === String::class.java) {
                StringConverter.INSTANCE
            } else null
            //其它类型我们不处理，返回null就行
        }

        companion object {
            private val INSTANCE = StringConverterFactory()

            fun create(): StringConverterFactory {
                return INSTANCE
            }
        }
    }
}
