package com.yongliang.houylbase

//import TestBuildGradle
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yongliang.houylbase.bean.NavbarBean
import com.yongliang.houylbase.databinding.ActivityMainBinding
import com.yongliang.houylbase.utils.DensityUtils
import com.yongliang.houylbase.utils.JSONLocalTools
import com.yongliang.houylbase.utils.Utils
import com.yongliang.houylbase.utils.tabs.TabLayout


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(
            binding.root
        )
        Utils.setOwnTranslucent(this)

        getDatas()
        initTabBottom()


//        p.swImageView.setBackgroundResource(R.mipmap.home)


    }
    //    val a = TestBuildGradle();
//    val p: PresenterImageView by lazy {
//        PresenterImageView(this)
//    };
    lateinit var listBean: List<NavbarBean>
    lateinit var binding: ActivityMainBinding
    lateinit var viewsss: View

    private fun getDatas() {
        val json = JSONLocalTools.getJson("data.json", this)
        val listType = object : TypeToken<ArrayList<NavbarBean>>() {}.type
//        //这里的json是字符串类型 = jsonArray.toString();
        try {
            var js = Gson().fromJson<List<NavbarBean>>(json, listType)
            listBean = js;
//            val listType = object : TypeToken<List<NavbarBean?>?>() {}.type
//            //这里的json是字符串类型 = jsonArray.toString();
//            //这里的json是字符串类型 = jsonArray.toString();
//            val t = Gson().fromJson<List<NavbarBean>>(json, listType)
            Log.e("getDatas", listBean.toString())

        } catch (e: Exception) {

        }

    }

    fun clickkk(view: View) {
//        binding.container.removeView(p.swImageView)
        val intentt= Intent()

        intentt.setClassName(
            "com.yongliang.houylbase",
            "com.yongliang.houylbase.constrantlayout.FullscreenActivity"
        )
        startActivity(intentt)
    }


    fun clickkkkk(view: View) {
//        container.addView(p.swImageView,200,100)
//        viewsss = LayoutInflater.from(this).inflate(R.layout.page_error, null);
//        val ivLeftLayoutParams: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.MATCH_PARENT
//        )
//        ivLeftLayoutParams.leftToLeft = 0
//        ivLeftLayoutParams.rightToRight = 0
//        ivLeftLayoutParams.topToTop = 0
//        ivLeftLayoutParams.bottomToBottom = 0
//
//        viewsss.layoutParams = ivLeftLayoutParams
//
//        viewsss.findViewById<ConstraintLayout>(R.id.cst_layout).setOnClickListener {
//
//            if (viewsss != null) {
//                binding.constaintLayoutMain.removeView(viewsss);
//            }
//
//        }
//
//        binding.constaintLayoutMain.addView(viewsss);

        val intentt= Intent()

        intentt.setClassName(
            "com.yongliang.houylbase",
            "com.yongliang.houylbase.constrantlayout.FullscreenActivity"
        )
        startActivity(intentt)
//        binding.constaintLayoutMain.removeView(view);
    }

    /**
     * 获取Tab 显示的内容
     *
     * @param context
     * @param position
     * @return
     */
    fun getTabView(context: Context, i: Int, bean: NavbarBean): View {
        var view = LayoutInflater.from(context).inflate(R.layout.layout_navgation_bottom, null)
        val tabIcon = view.findViewById<ImageView>(R.id.iv_bottom)
        val view_ = view.findViewById<View>(R.id.v_view)
        if (i == 2) {
            view_.visibility=View.VISIBLE
            val aa = DensityUtils.dip2px(context, 50.0f)
            val top = DensityUtils.dip2px(context, -40.0f)
            val lp = tabIcon.layoutParams

            lp.width = aa
            lp.height = aa
            view.layoutParams = lp
            Glide.with(context)
                .load(R.drawable.suixintou)
                .into(tabIcon)
//            tabIcon.setPadding(0,top,0,0)
        }else{
            Glide.with(context)
                .load(bean.prevC)
                .into(tabIcon)
        }

        val tabText = view.findViewById<TextView>(R.id.tv_bottom)
        tabText.text = if (TextUtils.isEmpty(bean.title)) "--" else bean.title
        return view
    }

    private fun initTabBottom() {
        binding.tabBottom.setClipChildren(false)
        binding.tabBottom.setClipToPadding(false)

        binding.tabBottom.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Toast.makeText(this@MainActivity, "akdfjlakj", Toast.LENGTH_SHORT).show()
                //改变Tab 状态
                // Tab 选中之后，改变各个Tab的状态
//                for (i in 0 until binding.tabBottom.tabCount) {
//                    val view = binding.tabBottom.getTabAt(i)!!.customView
//                    val icon = view!!.findViewById<ImageView>(R.id.iv_bottom)
//                    val text = view.findViewById<TextView>(R.id.tv_bottom)
//                    val bean: NavbarBean = listBean.get(i)
//                    if (i == tab.getPosition()) { // 选中状态
//                        text.setTextColor(resources.getColor(R.color.colorff5722))
//                        Glide.with(this@MainActivity)
//                            .load(bean.nextC)
//                            .into(icon)
//                    } else { // 未选中状态
//                        Glide.with(this@MainActivity)
//                            .load(bean.prevC)
//                            .into(icon)
//                        text.setTextColor(resources.getColor(R.color.color666666))
//                    }
//                }
               var text= tab?.view.findViewById<TextView>(R.id.tv_bottom)
                text?.setTextColor(resources.getColor(R.color.colorff5722))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Toast.makeText(this@MainActivity, "onTabUnselected", Toast.LENGTH_SHORT).show()
                var text= tab?.view?.findViewById<TextView>(R.id.tv_bottom)
                text?.setTextColor(resources.getColor(R.color.color333333))

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Toast.makeText(this@MainActivity, "onTabReselected", Toast.LENGTH_SHORT).show()
            }
        })
        // 设置OnTabChangeListener 需要在添加Tab之前，不然第一次不会回调onTabSelected()方法，
        binding.tabBottom.getSlidingTabIndicator().setClipChildren(false)
        binding.tabBottom.getSlidingTabIndicator().setClipToPadding(false)
        // 提供自定义的布局添加Tab
        binding.tabBottom.tabSelectedIndicator
        for (i in listBean.indices) {
          val tab=  binding.tabBottom.newTab()
                .setCustomView(
                    getTabView(
                        this@MainActivity, i, listBean.get(i)
                    )
                )

            binding.tabBottom.addTab(
                tab, false
            )
        }
        binding.tabBottom.getTabAt(0)!!.select() //默认选中第一个tab

    }
}