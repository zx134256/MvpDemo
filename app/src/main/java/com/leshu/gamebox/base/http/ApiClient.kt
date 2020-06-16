import com.leshu.gamebox.base.http.ApiService
import com.leshu.gamebox.base.http.RetrofitConfig

/**
 * 网络请求客户端
 *
 */

object ApiClient {
    var mApiServer: ApiService

    init {
        mApiServer = initConfig()
    }

    fun initConfig(): ApiService {
        val service = RetrofitConfig.mRetrofit.create(ApiService::class.java)
        return service
    }
}
