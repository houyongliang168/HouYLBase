package com.txx.app.main.section.bean



data class CommonTBean<T>(
        val rspCode: String,
        val rspDesc: String,
        var info: T,
        val url: String
)