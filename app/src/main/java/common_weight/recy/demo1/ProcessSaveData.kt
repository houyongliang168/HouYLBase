package common_weight.recy.demo1;

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yongliang.houylbase.APP
import common_weight.recy.CommonSPUtils
import org.json.JSONException


/**
 *
 * created by houyl
 * on  3:45 PM
 * 该界面数据 是更多展示的数据源，需要存本地然后再获取
 */
object ProcessSaveData {


    val flag = "ProcessFuncSaveData"

    //本地的数据
    var listCache: ArrayList<Item>;
    var spUtils: CommonSPUtils;


    init {
        listCache = ArrayList<Item>()
        spUtils = CommonSPUtils(APP.getContext(), "autoLogin") //获取相关内容
    }

//对外提供的方法
    fun getSaveList(): List<Item> {
//        将数据请求后缓存到本地，使用变量来控制是请求网络数据还是获取本地数据，考虑退出登录再次登录会有问题 ：获取的是上次数据 不是最新数据
        getCacheDatas();
        return listCache

    }


    // 获取本地存取的数据
    fun getSaveListFromJson(jsonString: String): List<Item>? {
        var ls: List<Item>? = null
        try {
            ls = Gson().fromJson(jsonString ?: "", object : TypeToken<List<Item>>() {}.type)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return ls
    }

    //保存网络请求的数据
    fun setListData(ls: List<Item>) {
       ls?.let {
           listCache?.clear()
           listCache?.addAll(ls)
       }
    }

    //保存网络请求的数据
    fun saveCacheDatas(ls: List<Item>) {
        val g = Gson()
        val jsonString = g.toJson(ls)?.toString()
        spUtils?.put(flag , jsonString ?: "")
    }

    //获取本地保存的数据
    fun getCacheDatas() {
        val cacheDatas = spUtils?.getString(flag , "")
        val ls = getSaveListFromJson(cacheDatas);
        ls?.let {
            if (it.size > 0) {
                listCache.clear()
                listCache.addAll(ls)
            }
        }
    }


}