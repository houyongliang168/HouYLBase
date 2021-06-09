package com.yongliang.houylbase

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import common_network.ApiService
import common_network.HttpUtil
import common_network.MoreFunctionsBean
import common_weight.recy.CommonSPUtils
import common_weight.recy.demo1.Item
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rx.Subscriber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/**
 *
 * created by houyl
 * on  3:45 PM
 * 处理更多功能
 *
 * 功能主键是
 */
object ProcessFuncContain {
    val flag = "ProcessFuncContainFlags"

    //本地的数据
    var listCache: ArrayList<Item>;

    //    保存本地的是json 串
    var spUtils: CommonSPUtils
    var ishavedGetData = false


    init {
        listCache = ArrayList<Item>()
        spUtils = CommonSPUtils(APP.getContext(), "autoLogin") //获取相关内容
    }

    //    必须在oncreate 中处理
    fun onCreate() {
//        重启oncreate 的时候需要初始化数据  防止登录后：获取的是上次数据 不是最新数据
        ishavedGetData = false
    }

    suspend fun getList(): List<Item> {
//        将数据请求后缓存到本地，使用变量来控制是请求网络数据还是获取本地数据，考虑退出登录再次登录会有问题 ：获取的是上次数据 不是最新数据
        if (ishavedGetData) {
            getCacheDatas();
        } else {
            getRequestData()
        }
        return listCache

    }

    suspend fun getRequestData() {
        val token =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIiLCJqdGkiOiIyXzFfMDEwMDAwNTEiLCJpc3MiOiJ0YWlrYW5nIiwiaWF0IjoxNjIxNTgyOTUxLCJzdWIiOiJhcHAiLCJuYmYiOjE2MjE1ODI5NTEsImV4cCI6MTYyMjE4Nzc1MSwic2VxTm8iOiIyMTA1MjExNTQyMzE4Mzk1MzYwNyJ9.Net1VYqteDt7PKRMTl8sP-TJ87EfjVd0zyqiDj92nUs"
        HttpUtil.init(HttpUtil.createService(
            ApiService::class.java,
            "https://isaleuat.taikang.com/"
        )
            .moreFunctions(
                token
            ), object : Subscriber<MoreFunctionsBean?>() {
            override fun onCompleted() {
            }

            override fun onError(e: Throwable) {

            }

            override fun onNext(moreFunctionsBean: MoreFunctionsBean?) {
//                MyLog.wtf ("zcc", "更多功能:" + moreFunctionsBean.toString ());
                when (moreFunctionsBean?.rspCode) {

                    "0" -> {

                        val infoBean = moreFunctionsBean.info
                        if (infoBean != null) {
                            val moreFunctionsBeanList = infoBean.moduleList
                        } else {
                        }

                    }
                    else -> {

                    }
                }

            }


        })
    }

    // 获取本地存取的数据
    fun getList(jsonString: String): List<Item>? {
        var ls: List<Item>? = null
        try {
            ls = Gson().fromJson(jsonString ?: "", object : TypeToken<List<Item>>() {}.type)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return ls
    }


    //保存网络请求的数据
    fun saveCacheDatas(ls: List<Item>) {
        val g = Gson()
        val jsonString = g.toJson(ls)?.toString()
        spUtils?.put(flag, jsonString ?: "")
    }

    //获取本地保存的数据
    fun getCacheDatas() {
        val cacheDatas = spUtils?.getString(flag, "")
        val ls = getList(cacheDatas);
        ls?.let {
            if (it.size > 0) {
                listCache.clear()
                listCache.addAll(ls)
            }
        }

    }

    suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>?, response: Response<T>?) {
                    val body = response?.body()
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("body is Null"))
                    }

                }

                override fun onFailure(call: Call<T>?, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })

        }

    }
    var gson: Gson = GsonBuilder().disableHtmlEscaping().create()
//    var mIndexInfoList = gson.toJson(personInfo.getInfo().getIndexInfoListNew())
//    var mFunctionList = gson.toJson(personInfo.getInfo().getFunctionList())

}