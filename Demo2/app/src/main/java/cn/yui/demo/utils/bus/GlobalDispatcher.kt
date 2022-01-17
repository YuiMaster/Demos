package cn.yui.demo.utils.bus

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @Description: 全局数据总线(不感知生命周期)
 * @Author: Yui Master
 * @CreateDate: 2022/1/17 3:47 下午
 */
class GlobalDispatcher {
    private val callbackList = ArrayList<CallbackItem<BusData>>()

    /**
     * 订阅数据
     * @param dataClass 订阅的数据类型
     * @param callback
     * @return 用于取消订阅
     */
    fun <T : BusData> subscribe(dataClass: Class<T>, callback: (T) -> Unit): (T) -> Unit {
        val item = CallbackItem(dataClass, callback) as CallbackItem<BusData>
        if (!callbackList.contains(item)) {
            callbackList.add(item)
        }
        return callback
    }

    /**
     * 取消订阅数据
     * @param callback 需要取消的订阅
     */
    fun <T : BusData> unSubscribe(callback: ((T) -> Unit)?) {
        if (callback == null) {
            return
        }
        val removeList = ArrayList<CallbackItem<BusData>>()
        for (item in callbackList) {
            if (item.callback == callback) {
                removeList.add(item)
            }
        }
        callbackList.removeAll(removeList)
    }

    /**
     * 分发事件,默认主线程
     *
     * @param data 数据
     * @param scope 数据处理线程
     */
    fun <T : BusData> notify(data: T, scope: CoroutineScope = MainScope()) {
        for (callback in callbackList) {
            if (callback.dataClass == data.javaClass) {
                scope.launch {
                    callback.callback.invoke(data)
                }
            }
        }
    }

    private class CallbackItem<T : BusData>(
        val dataClass: Class<T>,
        val callback: (T) -> Unit
    )
}
