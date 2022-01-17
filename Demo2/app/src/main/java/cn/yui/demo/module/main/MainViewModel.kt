package cn.yui.demo.module.main

import androidx.lifecycle.MutableLiveData
import cn.yui.demo.module.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Description: java类作用描述
 * @Author: Yui Master
 * @CreateDate: 2022/1/17 4:40 下午
 */
@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    val liveButtonText = MutableLiveData("点击")
}