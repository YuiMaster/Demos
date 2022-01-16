package cn.yui.demo.di

import cn.yui.demo.data.DataDispatcher
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
}