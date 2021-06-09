package com.txx.app.main.section.bean



data class CommonBean(
        val rspCode: String,
        val rspDesc: String,
        var info: Any,
        val url: String,
        val txxSignV1: String
)