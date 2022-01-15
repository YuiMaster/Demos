package cn.yui.demo

import android.app.Application
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

/**
 * @Description: java类作用描述
 * @Author: Yui Master
 * @CreateDate: 2022/1/15 7:44 下午
 */
@HiltAndroidApp
class NewApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}