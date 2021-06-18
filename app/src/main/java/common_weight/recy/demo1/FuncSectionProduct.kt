package common_weight.recy.demo1;
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.databinding.observable.DynamicChangeCallback
import com.widget.dialog.MyDialog
import com.yongliang.houylbase.R
import com.yongliang.houylbase.databinding.ProductMoreBinding
import com.yongliang.houylbase.utils.DensityUtils
import common_weight.recy.demo1.baseView.FragmentBaseSectionProduct
import common_weight.view.PagerConfig
import common_weight.view.PagerGridLayoutManager
import common_weight.view.PagerGridSnapHelper

/**
 * created by houyl
 * on  10:06 AM
 */
class FuncSectionProduct(context: Fragment, mBundle: Bundle) : FragmentBaseSectionProduct<ProductMoreBinding?, FuncSectionModel?>(context, mBundle) {

    private var mLayoutManager: PagerGridLayoutManager? = null
    private var mAdapter: FuncRecyclerviewAdapter? = null
    override fun getlayoutId(): Int {
        return R.layout.product_more
    }

    override fun initVariableId(): Int {
        return 0
    }

    override fun initViewObservable() {

        viewModel?.isShow?.observe(this, Observer {
            it?.let {
                if (it) {
                    showView()

//                    更新数据
                    viewModel?.list?.size.let {
                        var aa = it?.div(4)
                        if (aa != null && aa > 1) {

                            mLayoutManager = PagerGridLayoutManager(2, viewModel?.mColumns
                                ?: 5, PagerGridLayoutManager.HORIZONTAL)
                            binding?.recyMore?.layoutParams?.height=DensityUtils.dip2px(context,146f)

                        }else{

                            mLayoutManager = PagerGridLayoutManager( 1, viewModel?.mColumns
                                ?: 5, PagerGridLayoutManager.HORIZONTAL)
                            binding?.recyMore?.layoutParams?.height=DensityUtils.dip2px(context,73f)

                        }

                        binding?.recyMore?.layoutManager = mLayoutManager


                    }
                    mAdapter?.notifyDataSetChanged()

//                    pointAdapter?.notifyDataSetChanged()
                } else {
                    goneView()
                }
            }
        })


    }

    override fun setAdapter(view: View) {
//        获取视图并设置对应的点击事件
        //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法，里面有你要的Item对应的binding对象。
        // Adapter属于View层的东西, 不建议定义到ViewModel中绑定，以免内存泄漏
        setAdapter()
        setPointAdapter()
    }
    var pointAdapter:FuncPointRecyclerviewAdapter?=null
    private fun setPointAdapter() {
        if (context == null) {
            return
        }
        val lm = LinearLayoutManager(context)
        lm.orientation = LinearLayoutManager.HORIZONTAL
        binding?.recyMorePoint?.layoutManager = lm
        // 使用原生的 Adapter 即可
        val pointAdapter = FuncPointRecyclerviewAdapter(context, viewModel?.pointList)
        binding!!.recyMorePoint.adapter = pointAdapter
        viewModel?.pointList?.addOnListChangedCallback(DynamicChangeCallback(pointAdapter))
    }

    private fun setAdapter() {
        mLayoutManager = PagerGridLayoutManager( viewModel?.mRows
            ?: 2, viewModel?.mColumns
                ?: 5, PagerGridLayoutManager.HORIZONTAL)
        // 水平分页布局管理器
        mLayoutManager?.setPageListener(object : PagerGridLayoutManager.PageListener {
            override fun onPageSizeChanged(pageSize: Int) {
                Log.d("hyl", "pageSize:$pageSize")
                //                数量改变监听
                if (pageSize <= 1) {
                    binding?.recyMorePoint?.visibility = View.INVISIBLE
                } else {
                    binding?.recyMorePoint?.visibility = View.VISIBLE
                }
            }

            override fun onPageSelect(pageIndex: Int) {
                Log.d("hyl", "pageIndex:$pageIndex")
//             页面选中监听
                if (binding?.recyMorePoint?.visibility == View.VISIBLE) {
                    viewModel?.pointChangeProcess(pageIndex)
                }
            }
        }) // 设置页面变化监听器
        binding?.recyMore?.layoutManager = mLayoutManager


        // 设置滚动辅助工具
        val pageSnapHelper = PagerGridSnapHelper()
        pageSnapHelper.attachToRecyclerView(binding?.recyMore)

        // 如果需要查看调试日志可以设置为true，一般情况忽略即可
        PagerConfig.setShowLog(true)
        // 使用原生的 Adapter 即可
        mAdapter = FuncRecyclerviewAdapter(context, viewModel?.list)
        //        设置点击事件
        mAdapter?.listener = object : FuncRecyclerviewAdapter.OnClickListener {
            override fun OnClickListener(v: View?, possion: Int, bean: ProcessItemBean?) {

//                点击事件处理
                bean?.let {
                    if (it.displayNew != 1) {
//                    点击刷新新字图标
                        bean?.displayNew = 1
//                       保存数据
                        ProcessNew.saveNativeCode(bean?.item.funcId)
                        mAdapter?.notifyDataSetChanged()
//                        mAdapter?.notifyItemChanged(possion,"item更新")
//                        viewModel?.list?.set(possion,bean)
//                        mAdapter?.notifyItemRangeChanged(possion,1)
                    }
//                点击弹框处理
                    val item = it.item
                    val dialog = it.item?.dialog
                    dialog?.let {
                        if (it?.isShow == "1") {
                            showDialog(it?.dialogContent, bean)

                            return
                        }
                    }
                    //todo 点击事件待处理

//                    ClickEvent.clickEvent(item?.click, context);
                }

//                try {
//                    JSONObject properties = new JSONObject();
//                    properties.put("functionId", bean.getResourcePath());
//                    properties.put("buttonName", bean.getResourceName());
//                    properties.put("pageName", "首页");
//                    SensorsDataAPI.sharedInstance(App.getContext()).track(TCAgentEvent.HOME_FUNCTION_CLICK, properties);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }

            override fun OnLongClickListener(v: View?, possion: Int): Boolean {
                return false
            }

        }
        binding?.recyMore?.setHasFixedSize(true)
        //        使得数据 更新状态¬
        viewModel?.list?.addOnListChangedCallback(DynamicChangeCallback(mAdapter))
        binding?.recyMore?.adapter = mAdapter
    }

    fun showDialog(tip: String, bean: ProcessItemBean) {
        val myDialog = MyDialog(context)
        myDialog.showDialog(R.layout.dialog_verify)
        val tv_msg = myDialog.findViewById<View>(R.id.tv_warn_msg) as TextView
        tv_msg.setText(tip)
        myDialog.findViewById<View>(R.id.tv_warn_true).setOnClickListener {
            myDialog.dismiss()
//            0 不进入 其余进去
            if (bean?.item?.dialog?.isEnter != "0") {
//               ClickEvent.clickEvent(bean?.item?.click, context);
            }
        }
    }

    override fun onStop() {
        super.onStop()
//        在进入其他页面时，保存数据
        ProcessNew.saveMap()
    }
}