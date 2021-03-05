package com.yongliang.houylbase.hilt

import javax.inject.Inject

/**
 *
 * created by houyl
 * on  3:42 PM
 */
class Truck @Inject constructor(val driver: Driver) {
    /**
     * 不过现在还没结束，因为增加了Qualifier注解之后，所有为Engine类型进行依赖注入的地方也需要去声明注解，
     * 明确指定自己希望注入哪种类型的实例。

     */
    @BindGasEngine
    @Inject
    lateinit var gasEngine: Engine

    @BindElectricEngine
    @Inject
    lateinit var electricEngine: Engine
    fun deliver() {
        gasEngine.start()
        electricEngine.start()
        println("Truck is delivering cargo. Driven by $driver")
        gasEngine.shutdown()
        electricEngine.shutdown()
    }

}