package com.leshu.gamebox.base.http


import com.leshu.gamebox.BuildConfig
import com.leshu.gamebox.entity.AliUpdateEntity
import com.leshu.gamebox.entity.GameListEntity
import com.leshu.gamebox.entity.LoginResult
import com.leshu.gamebox.entity.UserDeviceEntity
import io.reactivex.Observable
import retrofit2.http.*


/**
 *
 *
 * 网络请求接口管理
 *
 *
 *问题记录
 * java.lang.IllegalArgumentException: No Retrofit annotation found. (parameter #4)
解决：看看自己的参数有没有声明类型。我的是忘了加@Field.
Post 请求 目前后台一直使用实体类进行传参
 */

interface ApiService {
    companion object {
        /**
         * 接口地址
         */
        val API_ADDRESS = BuildConfig.API_ADDRESS
    }

    /**
     * 微信登录
     */
    @FormUrlEncoded
    @POST("/wxlogin")
    fun login(@Field("code") wxCode: String, @Field("device") deviceId: String): Observable<LoginResult>

    @GET("/game/gameRank")
    fun getGameList(@Query("hotRank") rankType: String = "gameRank"): Observable<GameListEntity>

    @FormUrlEncoded
    @POST("/deviceLogin")
    fun deviceLogin(@Field("device") deviceId: String): Observable<UserDeviceEntity>

    @FormUrlEncoded
    @POST("/version/index")
    fun requestAliUpdate(@FieldMap aliUpdate : Map<String,String>) :Observable<AliUpdateEntity>
    /*  */
    /**
     * get
     *//*
    @GET("message/graph/render")
    fun getVerCode(@Header(TIME_STAMP) timestamp: Long, @Header(SIGN) sign: String, @Query("phone") phone: String): Observable<ResponseBody>

    */
    /**
     * post
     *//*
    @POST("admin/clientLog")
    fun uploadLog(@Header(TIME_STAMP) timestamp: Long, @Header(SIGN) sign: String, @Body jLogEntity: RequestBody): Observable<BaseResult>


    */
    /**
     * 表单 post
     *//*
    @POST("logistics/driver/departure")
    @FormUrlEncoded
    fun driverDeparture(@Header(TIME_STAMP) timestamp: Long, @Header(SIGN) sign: String, @Field("boxIds") boxIds: String): Observable<NewDriverDepartureResult>
*/


}