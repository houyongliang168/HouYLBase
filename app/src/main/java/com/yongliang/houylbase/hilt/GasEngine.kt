package com.yongliang.houylbase.hilt

import javax.inject.Inject

/**
 *
 * created by houyl
 * on  4:09 PM
 */
class GasEngine @Inject constructor():Engine {
    override fun start() {
        println("Gas engine start.")
    }

    override fun shutdown() {
        println("Gas engine shutdown.")
    }
}