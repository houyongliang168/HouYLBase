package common_weight.recy.demo1

import android.app.Application
import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.google.gson.Gson
import com.txx.app.main.section.bean.CommonBean
import common_network.HttpUtil
import common_weight.recy.demo1.baseView.BaseProductModel
import common_weight.recy.demo1.http.SectionService
import mvvm.BaseModel
import mvvm.SingleLiveEvent
import rx.Subscriber

/**
 * created by houyl
 * on  10:06 AM
 */
class FuncSectionModel : BaseProductModel<BaseModel?> {
    val TAG = this.javaClass.simpleName

    constructor(application: Application) : super(application) {}
    constructor(application: Application, model: BaseModel?) : super(application, model) {}

    //给RecyclerView添加ObservableList  需要展示的数据
    //headerFunction 处理头部编辑的数据
    var list: ObservableList<ProcessItemBean> = ObservableArrayList()

    //    小点处理
    var pointList: ObservableList<PointBean> = ObservableArrayList()

    var isShow = SingleLiveEvent<Boolean>()

    val mRows = 2
    val mColumns = 5
    private val maxNum = mRows * mColumns
    private var pages = 2

    override fun onCreate() {
        super.onCreate()
//      初始化本地数据
        ProcessNew
        ProcessSaveData
    }

    override fun loadData(mBundle: Bundle?) {
        super.loadData(mBundle)

        var uu = "https://isaleuat.taikang.com/isales-platform-home/homepage/commonFunctions"

        HttpUtil.init(HttpUtil.baseHttpUtils(SectionService::class.java, "https://www.baidu.com").commonFunctions(uu), object : Subscriber<CommonBean>() {
            override fun onCompleted() {}
            override fun onError(e: Throwable) {
                isShow.postValue(false)

            }

            override fun onNext(bean: CommonBean) {

                val info = Gson().toJson(bean?.info)
                val t = Gson().fromJson(info, FuncBean::class.java);
                t?.let {
                    it?.let {
                        var sb: ProcessItemBean
                        val ls = ArrayList<ProcessItemBean>()
                        for (i in it.items?.indices) {
                            sb = ProcessItemBean(it.items[i])

                            if (sb?.item?.isNew == "1") {
//                    如果之前处理过 则不处理 处理后 状态变为1  displayNew 状态只有 0  和 1 两种状态
//                    新字处理
                                val newCode = ProcessNew.getNew(sb.item?.funcId)
                                when (newCode) {
                                    0 -> {
                                        sb?.displayNew = 0
                                    }
                                    else -> {
                                        sb?.displayNew = 1
                                    }
//
                                }
                            } else {
                                sb?.displayNew = 1
                            }
                            sb?.let {
                                ls.add(it)
                            }

                        }
//            list.clear()
                        if (!ls.isEmpty()) {
                            list.addAll(ls)
                            isShow.postValue(true)
                            //           存入基本数据 用于后面数据展示 请求成功后更新数据
                            ProcessSaveData.saveCacheDatas(it.items)
                            pointStartAgainProcess()
                            return
                        }
//           存入基本数据 用于后面数据展示 请求成功后更新数据
//                        ProcessSaveData.saveCacheDatas(it.items)
//                        pointStartAgainProcess()

                    }


                }

                isShow.postValue(false)

            }
        })


//        val ss = JSONLocalTools.getJson("data_10.json", App.getApp())


//        val ls = Gson().fromJson(ss, FuncBean::class.java);
//        ls?.let {
//            var sb: ProcessItemBean
//            val ls = ArrayList<ProcessItemBean>()
//            for (i in it.items?.indices) {
//                sb = ProcessItemBean(it.items[i])
//
//                if (sb?.item?.isNew == "1") {
////                    如果之前处理过 则不处理 处理后 状态变为1  displayNew 状态只有 0  和 1 两种状态
////                    新字处理
//                    val newCode = ProcessNew.getNew(sb.item?.funcId)
//                    when (newCode) {
//                        0 -> {
//                            sb?.displayNew = 0
//                        }
//                        else -> {
//                            sb?.displayNew = 1
//                        }
////
//                    }
//                } else {
//                    sb?.displayNew = 1
//                }
//
//
//                sb?.let {
//                    ls.add(it)
//                }
//
//            }
////            list.clear()
//            if (!ls.isEmpty()) {
//                list.addAll(ls)
//            }
////           存入基本数据 用于后面数据展示 请求成功后更新数据
//            ProcessSaveData.saveCacheDatas(it.items)
//
//            pointStartAgainProcess()
//        }


    }

    /**
     * 点 重新处理
     */
    fun pointStartAgainProcess() {
        val pointNum: Int = list.size / maxNum
        val surplus: Int = list.size % maxNum
        if (surplus > 0) {
            pages = pointNum + 1
        }
        pointList.clear()
        var sb: PointBean
        for (i in 0 until pages) {
            sb = if (i == 0) {
                PointBean(true)
            } else {
                PointBean(false)
            }
            pointList.add(sb)
        }
    }

    //    滑动改变状态
    fun pointChangeProcess(page: Int) {
        for (i in pointList.indices) {
            if (i == page) {
                pointList[i].isSlected.set(true)
            } else {
                pointList[i].isSlected.set(false)
            }
        }
    }


}