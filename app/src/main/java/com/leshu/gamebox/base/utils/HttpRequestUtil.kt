package com.jiutong.base.utils

import com.leshu.gamebox.base.baseEntity.BaseEntity
import okhttp3.RequestBody

object HttpRequestUtil {
    /**
     * 公共的请求头,值不会动态改变
     * token 在登录之后传入
     */
    val publicHeader by lazy {
        mutableMapOf("tokens" to "")
    }


    /**
     * 转换RequestBody
     */
    fun convertRequestBody(requestBody: String): RequestBody {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), requestBody)
    }

    /**
     * 转换RequestBody
     */
    fun convertRequestBody(jEntity: BaseEntity): RequestBody {
        return convertRequestBody(GsonUtil.mGson.toJson(jEntity))
    }

    /**
     * 转换RequestBody
     */
    fun convertRequestBody(jEntitys: List<BaseEntity>): RequestBody {
        return convertRequestBody(GsonUtil.mGson.toJson(jEntitys))
    }

    fun convertFormEncode(){

    }
}