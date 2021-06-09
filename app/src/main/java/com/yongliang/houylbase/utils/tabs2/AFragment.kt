//package com.yongliang.houylbase.utils.tabs2
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.databinding.DataBindingUtil
//import androidx.databinding.ObservableArrayList
//import androidx.databinding.ObservableList
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.google.gson.Gson
//import com.widget.dialog.MyDialog
//import com.yongliang.houylbase.APP
//import com.yongliang.houylbase.R
//import com.yongliang.houylbase.databinding.FragmentABinding
//import com.yongliang.houylbase.utils.JSONLocalTools
//import common_weight.view.PagerConfig
//import common_weight.view.PagerGridLayoutManager
//import common_weight.view.PagerGridSnapHelper
//import mvvm.view.tablayout.DynamicChangeCallback
//
//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//lateinit var binding: FragmentABinding
//
///**
// * A simple [Fragment] subclass.
// * Use the [AFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
//class AFragment : Fragment() {
//    // TODO: Rename and change types of parameters
//    private var param1: Int? = null
//    private var param2: String? = null
//    val ls = ArrayList<String>();
//    var list: ObservableList<ProcessItemBean> = ObservableArrayList()
//
//    //    小点处理
//    var pointList: ObservableList<PointBean> = ObservableArrayList()
//    private var mLayoutManager: PagerGridLayoutManager? = null
//    private var mAdapter: FuncRecyclerviewAdapter? = null
//    val mRows = 2
//    val mColumns = 5
//    private val maxNum = mRows * mColumns
//    private var pages = 2
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getInt(ARG_PARAM1)
////            param2 = it.getString(ARG_PARAM2)
//        }
//        ls.add("ahkdjfhkajhdf")
//        ls.add("ahkdjfhkajhdf")
//        ls.add("ahkdjfhkajhdf")
//        ls.add("ahkdjfhkajhdf")
//        ls.add("ahkdjfhkajhdf")
//        loadData(null)
//    }
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_a, container, false)
//        binding = DataBindingUtil.bind(view)!!
//        // Inflate the layout for this fragment
//        return binding.root
//    }
//
//
//    private fun setPointAdapter() {
//        if (context == null) {
//            return
//        }
//        val lm = LinearLayoutManager(context)
//        lm.orientation = LinearLayoutManager.HORIZONTAL
//        binding?.recyMorePoint?.layoutManager = lm
//        // 使用原生的 Adapter 即可
//        val pointAdapter = FuncPointRecyclerviewAdapter(this, pointList)
//        binding!!.recyMorePoint.adapter = pointAdapter
//        pointList?.addOnListChangedCallback(DynamicChangeCallback(pointAdapter))
//
//    }
//
//    private fun setAdapter() {
//        mLayoutManager = PagerGridLayoutManager(
//            mRows ?: 2, mColumns
//                ?: 5, PagerGridLayoutManager.HORIZONTAL
//        )
//        // 水平分页布局管理器
//        mLayoutManager?.setPageListener(object : PagerGridLayoutManager.PageListener {
//            override fun onPageSizeChanged(pageSize: Int) {
//                Log.d("hyl", "pageSize:$pageSize")
//                //                数量改变监听
//                if (pageSize <= 1) {
//                    binding?.recyMorePoint?.visibility = View.INVISIBLE
//                } else {
//                    binding?.recyMorePoint?.visibility = View.VISIBLE
//                }
//            }
//
//            override fun onPageSelect(pageIndex: Int) {
//                Log.d("hyl", "pageIndex:$pageIndex")
////             页面选中监听
//                if (binding?.recyMorePoint?.visibility == View.VISIBLE) {
//                   pointChangeProcess(pageIndex)
//                }
//            }
//        }) // 设置页面变化监听器
//        binding?.recyMore?.layoutManager = mLayoutManager
//        binding?.recyMore?.setHasFixedSize(true)
//
//        // 设置滚动辅助工具
//        val pageSnapHelper = PagerGridSnapHelper()
//        pageSnapHelper.attachToRecyclerView(binding?.recyMore)
//
//        // 如果需要查看调试日志可以设置为true，一般情况忽略即可
//        PagerConfig.setShowLog(true)
//        // 使用原生的 Adapter 即可
//            mAdapter = FuncRecyclerviewAdapter(this,list)
//
//        //        设置点击事件
//        mAdapter?.listener = object : FuncRecyclerviewAdapter.OnClickListener {
//            override fun OnClickListener(v: View?, possion: Int, bean: ProcessItemBean?) {
//
////                点击事件处理
//                bean?.let {
//                    if (it.displayNew != 1) {
////                    点击刷新新字图标
//                        bean?.displayNew = 1
////                        mAdapter?.notifyItemChanged(possion)
//
//                        mAdapter?.notifyItemChanged(possion,"item更新")
////                        mAdapter?.notifyItemRangeChanged(possion,1)
//                    }
////                点击弹框处理
//                    val item = it.item
//                    val dialog = it.item?.dialog
//                    dialog?.let {
//                        if (it?.isShow == "1") {
//                            showDialog(it?.dialogContent, bean)
//                            return
//                        }
//                    }
//                }
//
////                try {
////                    JSONObject properties = new JSONObject();
////                    properties.put("functionId", bean.getResourcePath());
////                    properties.put("buttonName", bean.getResourceName());
////                    properties.put("pageName", "首页");
////                    SensorsDataAPI.sharedInstance(App.getContext()).track(TCAgentEvent.HOME_FUNCTION_CLICK, properties);
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
//            }
//
//            override fun OnLongClickListener(v: View?, possion: Int): Boolean {
//                return false
//            }
//
//        }
//        //        使得数据 更新状态¬
//       list?.addOnListChangedCallback(DynamicChangeCallback(mAdapter))
//        binding?.recyMore?.adapter = mAdapter
////        binding?.recyMore?.setHasFixedSize(false)
////        binding?.recyMorePoint?.itemAnimator=null
//    }
//
//    fun showDialog(tip: String, bean: ProcessItemBean) {
//        val myDialog = MyDialog(context)
//        myDialog.showDialog(R.layout.dialog_verify)
//        val tv_msg = myDialog.findViewById<View>(R.id.tv_warn_msg) as TextView
//        tv_msg.setText(tip)
//        myDialog.findViewById<View>(R.id.tv_warn_true).setOnClickListener {
//            myDialog.dismiss()
////            0 不进入 其余进去
//            if (bean?.item?.dialog?.isEnter != "0") {
//            }
//        }
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//
//        super.onViewCreated(view, savedInstanceState)
////        recycler_view = view.findViewById(R.id.recycler_view)
////        //      设置视图及数据
////        val manager: RecyclerView.LayoutManager =
////            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
////
////        recycler_view.layoutManager = manager
////        val adapter = StringAdapter(getContext(), ls);
////
////        recycler_view.setAdapter(adapter);
////
////        when (param1) {
////            (1) -> view.setBackgroundColor(Color.RED)
////            (2) -> view.setBackgroundColor(Color.GREEN)
////            (3) -> view.setBackgroundColor(Color.BLUE)
////            (4) -> view.setBackgroundColor(Color.YELLOW)
////
////        }
//        setAdapter()
//        setPointAdapter()
//    }
//
//     fun loadData(mBundle: Bundle?) {
//        val ss = JSONLocalTools.getJson("data_10.json", APP.getContext())
//        val ls = Gson().fromJson(ss, FuncBean::class.java);
//        ls?.let {
//            var sb: ProcessItemBean
//            val ls = ArrayList<ProcessItemBean>()
//            for (i in it.items?.indices) {
//                sb = ProcessItemBean(it.items[i])
//                ls.add(sb)
//            }
//            list.addAll(ls)
//            pointStartAgainProcess()
//        }
//
//
//    }
//
//    /**
//     * 点 重新处理
//     */
//    fun pointStartAgainProcess() {
//        val pointNum: Int = list.size / maxNum
//        val surplus: Int = list.size % maxNum
//        if (surplus > 0) {
//            pages = pointNum + 1
//        }
//        pointList.clear()
//        var sb: PointBean
//        for (i in 0 until pages) {
//            sb = if (i == 0) {
//                PointBean(true)
//            } else {
//                PointBean(false)
//            }
//            pointList.add(sb)
//        }
//    }
//
//    //    滑动改变状态
//    fun pointChangeProcess(page: Int) {
//        for (i in pointList.indices) {
//            if (i == page) {
//                pointList[i].isSlected.set(true)
//            } else {
//                pointList[i].isSlected.set(false)
//            }
//        }
//    }
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment AFragment.  , param2: String
//         */
//        @JvmStatic
//        fun newInstance(param1: Int) =
//            AFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_PARAM1, param1)
////                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
//}