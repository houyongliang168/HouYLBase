package com.yongliang.houylbase

import PresenterImageView
//import TestBuildGradle
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
//    val a = TestBuildGradle();
    val p: PresenterImageView by lazy {
        PresenterImageView(this)
    };

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_main
        )


        p.swImageView.setBackgroundResource(R.mipmap.home)


    }

    fun clickkk(view: View) {
        container.removeView( p.swImageView)
    }

    fun clickkkkk(view: View) {
        container.addView(p.swImageView,200,100)
    }
}