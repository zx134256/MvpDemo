package com.jiutong.base.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

/**
 * @author: xiangyun_liu
 *
 * @date: 2018/8/3 17:02
 */
object GsonUtil {
    val mGson by lazy { initConfig() }

    private fun initConfig(): Gson {
        val gsonBuilder = GsonBuilder()
        /**
         * 配置Gson
         * 1.excludeFieldsWithoutExposeAnnotation() 对没有用@Expose注解的变量不进行解析，或者对根据使用的@Expose注解的serialize
         *  （序列化）和deserialize（反序列化）值进行判断解析，@Expose属性值不写默认为true；
         * 2.serializeNulls() Gson在默认情况下是不主动导出值为null的键的,需要手动设置serializeNulls()；
         * 3.setDateFormat() 设置日期格式，序列化和反序列化时均有效
         * 4.disableInnerClassSerialization() 禁止序列化内部类
         * 5.disableHtmlEscaping() 禁止转义html标签
         * 6.setPrettyPrinting() 格式化输出
         * 7.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory<>()) 手动管理String类型的序列化和反序列化，将null替
         *   换成""
         */
        gsonBuilder.excludeFieldsWithoutExposeAnnotation()
//        gsonBuilder.serializeNulls()
        gsonBuilder.setDateFormat("yyyy-MM-dd")
//        gsonBuilder.disableInnerClassSerialization()
        gsonBuilder.disableHtmlEscaping()
        gsonBuilder.setPrettyPrinting()
        gsonBuilder.registerTypeAdapterFactory(NullStringToEmptyAdapterFactory())
        return gsonBuilder.create()
    }

    /**
     * 针对String类型进行手动管理序列化和反序列化
     */
    internal class NullStringToEmptyAdapterFactory : TypeAdapterFactory {

        override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            val rawType = type.rawType
            return if (rawType == String::class.java) {
                StringNullAdapter() as TypeAdapter<T>
            } else null
        }
    }

    /**
     * 手动管理序列化和反序列化
     */
    internal class StringNullAdapter : TypeAdapter<String>() {
        @Throws(IOException::class)
        override fun read(reader: JsonReader): String {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull()
                return ""
            }
            return reader.nextString()
        }

        @Throws(IOException::class)
        override fun write(writer: JsonWriter, value: String?) {
            if (value == null) {
                writer.value("")
                return
            }
            writer.value(value)
        }
    }
}