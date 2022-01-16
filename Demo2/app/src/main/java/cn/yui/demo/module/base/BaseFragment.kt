package cn.yui.demo.module.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @Description: java类作用描述
 * @Author: Yui Master
 * @CreateDate: 2022/1/16 3:33 下午
 */
open class BaseFragment<T : ViewDataBinding>(@LayoutRes private val layoutId: Int) : Fragment() {
    protected lateinit var dataBinding: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(requireActivity(), layoutId)
    }
}