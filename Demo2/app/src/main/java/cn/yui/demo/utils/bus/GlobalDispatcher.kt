package cn.yui.demo.utils.bus

/**
 * @Description: 全局数据总线(不感知生命周期)
 * @Author: Yui Master
 * @CreateDate: 2022/1/17 3:47 下午
 */
class GlobalDispatcher {
    private val callbackList = ArrayList<CallbackItem<BusData>>()

    /**
     * @param T
     * @param rxDataClass
     * @param callback
     * @return 用于取消订阅
     */
    fun <T : BusData> subscribe(rxDataClass: Class<T>, callback: (T) -> Unit): (T) -> Unit {
        val item = CallbackItem(rxDataClass, callback) as CallbackItem<BusData>
        if (!callbackList.contains(item)) {
            callbackList.add(item)
        }
        return callback
    }

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

    fun <T : BusData> notify(rxData: T) {
        for (callback in callbackList) {
            if (callback.rxDataClass == rxData.javaClass) {
                callback.callback.invoke(rxData)
            }
        }
    }

    private class CallbackItem<T : BusData>(
        val rxDataClass: Class<T>,
        val callback: (T) -> Unit
    )

}
