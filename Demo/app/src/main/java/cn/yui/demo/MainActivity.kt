package cn.yui.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cn.yui.demo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Description: 主页面
 * @Author: Yui Master
 * @CreateDate: 2022/1/15 7:23 下午
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}