package cn.yui.demo.module.user

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import cn.yui.demo.R
import cn.yui.demo.data.db.items.UserInfoItem
import cn.yui.demo.databinding.ActivityUserRegisterBinding
import cn.yui.demo.module.base.BaseActivity
import cn.yui.demo.utils.LOG
import cn.yui.demo.utils.bus.GlobalDispatcher
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @Description: 用户注册
 * @Author: Yui Master
 * @CreateDate: 2022/1/16 4:18 下午
 */
@AndroidEntryPoint
class UserRegisterActivity :
    BaseActivity<ActivityUserRegisterBinding>(R.layout.activity_user_register) {

    @Inject
    lateinit var globalDispatcher: GlobalDispatcher
    private var subscribeUserInfo: ((UserInfoItem) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.titleBarBack.setOnClickListener { onBackPressed() }
        subscribeUserInfo = globalDispatcher.subscribe(UserInfoItem::class.java) {
            LOG.d("UserRegisterActivity", "" + it.userName)
        }

        globalDispatcher.notify(UserInfoItem("YuiMaster"))
    }

    override fun onDestroy() {
        super.onDestroy()
        globalDispatcher.unSubscribe(subscribeUserInfo)
    }

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, UserRegisterActivity::class.java)
            activity.startActivity(intent)
        }
    }
}