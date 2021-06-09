package com.txx.app.main.section.clickEvent

/**
 *
 * created by houyl
 * on  10:04 AM
 * 点击事件内部处理
 *
 * commonTyle 通用属性   默认为0，是通用型  1 为不通用 需要单独处理
 *
 * neadParameters 所需要的参数
 *
 * funName 功能名称
 * nativeCode 功能唯一标识
 * pararms 点击事件传递接受的参数
 * neadParameters 点击事件原生需要的参数
 *
 * 当这一套不满足需求，commonTyle 为1 ，在点击事件中单独处理事件
 *
 * backups 存两个备份字段，方便以后维护
 *
 * 数据库中需要存的值
 *
 *
 */
data class NativeClickModule(var nativeCode: String="",
                             var funName: String="",
                             var pararms: String="{}",
                             var type: String="native",
                             var commonTyle: Int=0,
                             var pluginName: String="",
                             var className: String="",
                             var neadParameters: String="{}",
                             var backupsOne: String="",
                             var backupsTwo: String=""
)
