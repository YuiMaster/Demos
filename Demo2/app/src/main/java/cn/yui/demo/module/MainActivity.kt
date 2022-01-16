package cn.yui.demo.module

import android.os.Bundle
import cn.yui.demo.R
import cn.yui.demo.databinding.ActivityMainBinding
import cn.yui.demo.module.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Description: 主页面
 * @Author: Yui Master
 * @CreateDate: 2022/1/15 7:23 下午
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.textView.text = "测试内容"
    }
}