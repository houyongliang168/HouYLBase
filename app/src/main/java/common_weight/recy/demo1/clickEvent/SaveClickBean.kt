package com.txx.app.main.section.clickEvent

/**
 *
 * created by houyl
 * on  10:27 AM
 *
 * 不同插件间跳转调用，考虑将数据存入本地数据库中，从数据库中存取数
 */
object  SaveClickBean {

    val list:ArrayList<NativeClickModule>;
    init {
        list=ArrayList<NativeClickModule>();
//        相关数据在这里写 查找相关数据库数据获取集
    }

    fun saveClickBean(){
        list.clear()
        getNativeList();
//     调用清除数据方法
//     调用存数据方法，将list 存room 里面





    }

  fun  getNativeList(){
//      添加功能 示例代码
      var clickBean=NativeClickModule(nativeCode = "0000000",pluginName = "",className = "",funName = "跳转首页搜索");
      list.add(clickBean);
       clickBean=NativeClickModule();
      list.add(clickBean);
  }




}