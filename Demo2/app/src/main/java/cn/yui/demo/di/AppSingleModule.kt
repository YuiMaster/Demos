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

    @Singleton
    @Provides
    fun provideDataDispatcher(): DataDispatcher {
        return DataDispatcher()
    }

    @Singleton
    @Provides
    fun provideGlobalDispatcher(): GlobalDispatcher {
        return GlobalDispatcher()
    }
}