package cn.yui.demo.utils.bus

import cn.yui.demo.utils.LOG
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * @Description: 全局数据发送总线(基于RxJava)，
 * 优点：可以指定线程切换
 * 缺点：
 * 方式1：第一个订阅取完数据时移除数据，导致其他订阅空指针异常
 * 方式2：取完数据不移除数据，导致数据堆积
 * @Author: Yui Master
 * @CreateDate: 2022/1/16 12:40 下午
 */
class DataDispatcher {
    companion object {
        private const val TAG = "DataDispatcher"
    }

    /** 传输数据存储对象 */
    private var mMap: ConcurrentMap<Class<*>, BusData> = ConcurrentHashMap()

    /** 一个Observable对象 */
    private val mPublishData: PublishSubject<Class<*>> = PublishSubject.create()

    /**
     * 存储useCase
     * 使用save [busData]是要存继承UseCase类的方法
     * */
    fun save(busData: BusData) {
        LOG.d(TAG, "save ")
        val busDataClass: Class<BusData> = busData.javaClass
        mMap[busDataClass] = busData
        mPublishData.onNext(busDataClass)

        mPublishData.subscribe()
    }

    /**
     * 接收RxData
     * @param rxDataClass RxData 实现类
     * @return mMap 对应Key的value
     * */
    fun <T : BusData> get(rxDataClass: Class<T>): T? {
        return mMap.remove(rxDataClass) as T?
    }

    /**
     * 是否存在指定的数据
     * @param rxDataClass rxData 实现类
     * @return true：存在 false：不存在
     */
    fun <T : BusData> isContain(rxDataClass: Class<T>): Boolean {
        return mMap.containsKey(rxDataClass)
    }

    /**
     * 消费数据：取得数据的同时，从[mMap]移除该数据
     * 接收 rxData，返回Observable对象
     * 如果传入的[rxDataClass]是继承RxData类那么从 mMap数据组中拿出并返回
     */
    fun <T : BusData> observeRxData(rxDataClass: Class<T>): Observable<T> {
        return mPublishData.filter { clazz -> return@filter clazz == rxDataClass }
            .map { return@map mMap.remove(rxDataClass) }
            .cast(rxDataClass)
    }

    /**
     * 不消费:取得数据的同时，[mMap]中仍然存在该数据
     * 接收RxData，返回Observable对象
     * 如果传入的[rxDataClass]是继承RxData类那么从 mMap 数据组中拿出并返回
     */
    fun <T : BusData> observeRxDataUnRemove(rxDataClass: Class<T>): Observable<T> {
        return mPublishData.filter { clazz -> return@filter clazz == rxDataClass }
            .map {
                LOG.d(TAG, "RxDataObservableUnRemove ")
                return@map mMap[rxDataClass]
            }
            .cast(rxDataClass)
    }
}