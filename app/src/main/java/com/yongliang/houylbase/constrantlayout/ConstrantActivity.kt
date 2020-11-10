package com.yongliang.houylbase.constrantlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.yongliang.houylbase.R

/**
 * ConstraintLayout 练习相关内容
 *  ConstraintLayout 动态修改相关内容
 */
class ConstrantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_constrant)
        setContentView(R.layout.constraintlayout_pagerwork)
        //        动态修改布局内容
        findViewById<ConstraintLayout>(R.id.cl_bottom)
        ?.let {
            //初始化一个ConstraintSet
            val set = ConstraintSet()
            //将原布局复制一份
            set.clone(it)
            //分别将“中间按钮”START方向和BOTTOM方向的约束清除
            set.clear(R.id.tv_bottom_3, ConstraintSet.LEFT)
            set.clear(R.id.tv_bottom_3, ConstraintSet.RIGHT)
            //重新建立新的约束
            //“中间按钮”的END约束“按钮2”控件的START
            //相当于 app:layout_constraintEnd_toStartOf="@id/按钮2"
            set.connect(
                    R.id.tv_bottom_3,
                    ConstraintSet.END,
                    R.id.tv_bottom_2,
                    ConstraintSet.START,
                    resources.getDimensionPixelSize(R.dimen.margin10)
            )
            //以及底部方向的约束
            //最后将更新的约束应用到布局
            set.applyTo(it)
        }

    }


}