package cn.yui.demo.data.net.api

import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Description: 用户服务请求
 * @Author: Yui Master
 * @CreateDate: 2022/1/16 11:40 上午
 */
@Singleton
class UserRepository @Inject constructor(
    private val userService: UserService
) {

    suspend fun login(userName: String, passwd: String): String {
        return userService.login(userName, passwd)
    }

    suspend fun register(userName: String, passwd: String, rePassword: String): String {
        return userService.register(userName, passwd, rePassword)
    }

    suspend fun logout(): String {
        return userService.logout()
    }
}