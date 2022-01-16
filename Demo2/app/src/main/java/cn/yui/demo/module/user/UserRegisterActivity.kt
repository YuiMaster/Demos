package cn.yui.demo.module.user

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import cn.yui.demo.R
import cn.yui.demo.databinding.ActivityUserRegisterBinding
import cn.yui.demo.module.base.BaseActivity

/**
 * @Description: 用户注册
 * @Author: Yui Master
 * @CreateDate: 2022/1/16 4:18 下午
 */
class UserRegisterActivity :
    BaseActivity<ActivityUserRegisterBinding>(R.layout.activity_user_register) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.titleBarBack.setOnClickListener { onBackPressed() }
    }

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, UserRegisterActivity::class.java)
            activity.startActivity(intent)
        }
    }
}