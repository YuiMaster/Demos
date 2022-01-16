package cn.yui.demo.di

import cn.yui.demo.data.net.api.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Description: 依赖注入，集成用户服务
 * @Author: Yui Master
 * @CreateDate: 2022/1/16 11:36 上午
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideUserService(): UserService {
        return UserService.create()
    }
}