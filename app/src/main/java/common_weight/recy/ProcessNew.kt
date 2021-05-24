package common_weight.recy

import com.yongliang.houylbase.APP
import org.json.JSONException
import org.json.JSONObject


/**
 *
 * created by houyl
 * on  3:45 PM
 * 处理新字 首页功能区和更多页面都调用这个方法
 */
object ProcessNew {
    /**
     *  map 中 value 值
     *  0 标识存过数据
     */

    var map: HashMap<String, Int>;
//    保存本地的是json 串

    var spUtils: CommonSPUtils
    var newFlags: String

    init {
        map = HashMap()
        spUtils = CommonSPUtils(APP.getContext(), "autoLogin") //获取相关内容
        newFlags = spUtils.getString("processNewFlags", "")
        getMap();
    }

    fun save(nativeCode: String) {
        map.put(nativeCode, 0)
    }


    fun getMap(jsonString: String?): HashMap<String, Any>? {
        val jsonObject: JSONObject
        try {
            jsonObject = JSONObject(jsonString)
            val keyIter: Iterator<String> = jsonObject.keys()
            var key: String
            var value: Any
            var valueMap = HashMap<String, Any>()
            while (keyIter.hasNext()) {
                key = keyIter.next()
                value = jsonObject[key] as Any
                valueMap[key] = value
            }
            return valueMap
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    fun getMapInt(jsonString: String?): HashMap<String, Int>? {
        val jsonObject: JSONObject
        try {
            jsonObject = JSONObject(jsonString)
            val keyIter: Iterator<String> = jsonObject.keys()
            var key: String
            var value: Any
            var valueMap = HashMap<String, Int>()
            while (keyIter.hasNext()) {
                key = keyIter.next()
                value = jsonObject[key] as Int
                valueMap[key] = value
            }
            return valueMap
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }


    fun getMap() {
        val map1 = getMapInt(newFlags);
        map.clear()
        map1?.let {
            map.putAll(it)
        }
    }

    fun saveMap() {
        val json = JSONObject(map as Map<*, *>).toString()
        spUtils.put("processNewFlags", json)
    }


    fun getCode(nativeCode: String): Int {
        val value = map.get(nativeCode)
        return value ?: -1
    }

    //    再处理  0 要显示 新  其他不显示新
    fun getNew(nativeCode: String): Int {
        val value = getCode(nativeCode);
//        第一次取 没有值
        if (value == -1) {
            save(nativeCode)
            saveMap()
            return 0
        } else {
            return -1
        }
    }

}