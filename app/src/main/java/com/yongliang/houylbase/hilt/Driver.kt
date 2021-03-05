package com.yongliang.houylbase.hilt

import com.yongliang.houylbase.APP
import javax.inject.Inject

/**
 *
 * created by houyl
 * on  4:02 PM
 */
class Driver@Inject constructor(val application: APP)  {
    init {
        println("Driver is begining.")
    }

}