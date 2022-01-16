package cn.yui.demo.data.net.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @Description: java类作用描述
 * @Author: Yui Master
 * @CreateDate: 2022/1/16 11:25 上午
 */
interface UserService {
    /**  登录 */
    @POST("user/login")
    suspend fun login(userName: String, passwd: String): String

    /**  注册 */
    @POST("user/register")
    suspend fun register(userName: String, passwd: String, repassword: String): String

    /**  退出登录 */
    @GET("logout/json")
    suspend fun logout(): String

    companion object {
        private const val BASE_URL = "https://www.wanandroid.com/"

        fun create(): UserService {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserService::class.java)
        }
    }
}