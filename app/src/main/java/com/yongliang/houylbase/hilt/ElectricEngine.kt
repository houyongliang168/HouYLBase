package com.yongliang.houylbase.hilt

import javax.inject.Inject

/**
 *
 * created by houyl
 * on  4:11 PM
 */
class ElectricEngine @Inject constructor():Engine {
    override fun start() {
        println("Electric engine start.")
    }

    override fun shutdown() {
        println("Electric engine shutdown.")
    }
}