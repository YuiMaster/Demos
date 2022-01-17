package cn.yui.demo.di

import cn.yui.demo.utils.bus.DataDispatcher
import cn.yui.demo.utils.bus.GlobalDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppSingleModule {

    /** 全局数据分发：基于RxJava */
    @Singleton
    @Provides
    fun provideDataDispatcher(): DataDispatcher {
        return DataDispatcher()
    }

    /** 全局数据分发 */
    @Singleton
    @Provides
    fun provideGlobalDispatcher(): GlobalDispatcher {
        return GlobalDispatcher()
    }
}