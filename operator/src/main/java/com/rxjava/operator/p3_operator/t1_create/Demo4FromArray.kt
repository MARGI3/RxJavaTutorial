package com.rxjava.operator.p3_operator.t1_create

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * author : magic
 * date   : 11/03/2019
 * mail   : 562224864cross@gmail.com
 */

/**
 * 快速创建1个被观察者对象（Observable）
 * 发送事件的特点：直接发送 传入的数组数据
 *
 * 会将数组中的数据转换为Observable对象
 *
 * 快速创建 被观察者对象（Observable） & 发送10个以上事件（数组形式）
 * 数组元素遍历
 */
class Demo4FromArray : ItemRunnable() {

    override fun run() {
        super.run()

        val array = arrayOf(1, 2, 3, 4, 5, 6, 7)

        /**
         * 扩展操作符
         * NOTE *array to separate array object to java variable size param
         * NOTE *array 把Array对象转换成java可变长变量
         */
        val observable = Observable.fromArray(*array)

        observable.subscribe(object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "start subscribe")
            }

            override fun onNext(t: Int) {
                Log.d(TAG, "receive event $t")
            }

            override fun onComplete() {
                Log.d(TAG, "handle complete")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "handle error ${e.message}")
            }
        })

    }
}


