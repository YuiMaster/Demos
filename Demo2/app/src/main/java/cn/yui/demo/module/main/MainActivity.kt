package cn.yui.demo.module.main

import android.os.Bundle
import androidx.activity.viewModels
import cn.yui.demo.R
import cn.yui.demo.data.db.items.LoginInfoItem
import cn.yui.demo.data.db.items.UserInfoItem
import cn.yui.demo.databinding.ActivityMainBinding
import cn.yui.demo.module.base.BaseActivity
import cn.yui.demo.module.user.UserRegisterActivity
import cn.yui.demo.utils.LOG
import cn.yui.demo.utils.bus.GlobalDispatcher
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @Description: 主页面
 * @Author: Yui Master
 * @CreateDate: 2022/1/15 7:23 下午
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    @Inject
    lateinit var globalDispatcher: GlobalDispatcher
    private var subscribeUserInfo: ((UserInfoItem) -> Unit)? = null
    private var subscribeLoginInfoItem: ((LoginInfoItem) -> Unit)? = null

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.viewModel = viewModel
        dataBinding.textView.text = "测试内容"
        dataBinding.textView.setOnClickListener {
            UserRegisterActivity.start(this)
        }
        subscribeUserInfo = globalDispatcher.subscribe(UserInfoItem::class.java) {
            LOG.d("MainActivity", "UserInfoItem " + it.userName)
        }
        subscribeLoginInfoItem = globalDispatcher.subscribe(LoginInfoItem::class.java) {
            LOG.d("MainActivity", "LoginInfoItem " + it.userName)
        }
        dataBinding.button.setOnClickListener {
            globalDispatcher.notify(LoginInfoItem("LoginInfoBean"))
            globalDispatcher.unSubscribe(subscribeLoginInfoItem)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        globalDispatcher.unSubscribe(subscribeUserInfo)
    }
}