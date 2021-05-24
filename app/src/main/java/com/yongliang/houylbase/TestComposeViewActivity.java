package com.yongliang.houylbase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.utils.log.MyToast;
import com.yongliang.houylbase.utils.BlankFragment;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static androidx.constraintlayout.widget.ConstraintProperties.PARENT_ID;


public class TestComposeViewActivity extends AppCompatActivity {

    private LinearLayout ll_contain;
    private ConstraintLayout cl;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_compose_view);
        ll_contain = findViewById(R.id.ll_contain);
        cl = findViewById(R.id.cl);
        btn = findViewById(R.id.btn);
        initView();

    }

    private void initView() {
        FragmentManager fragmentManager=getSupportFragmentManager();

        FragmentTransaction ft= fragmentManager.beginTransaction();
        ft.add(R.id.ll_contain, BlankFragment.newInstance());
        ft.commitAllowingStateLoss();

//        View mErrorView = LayoutInflater.from (this).inflate (R.layout.page_error, null);
//        ConstraintLayout.LayoutParams errorLayoutParams = new ConstraintLayout .LayoutParams (
//                ConstraintLayout.LayoutParams.MATCH_PARENT,
//                ConstraintLayout.LayoutParams.MATCH_PARENT
//        );
//        errorLayoutParams.topToTop=PARENT_ID;
//        errorLayoutParams.leftToLeft=PARENT_ID;
//        errorLayoutParams.rightToRight=PARENT_ID;
//        errorLayoutParams.bottomToBottom=PARENT_ID;
//        mErrorView.setLayoutParams (errorLayoutParams);
//        cl.addView(mErrorView);

//        View mErrorView = LayoutInflater.from (this).inflate (R.layout.product_navigation, null);
//        cl.addView(mErrorView);
//        showGuideView();
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyToast.showShort("adfadf");
//            }
//        });

    }

    public void showGuideView() {
//        蒙层效果

        View view = getWindow().getDecorView();
        if (view == null) {
            return;
        }
//        ViewParent viewParent = view.getParent();
        if (view instanceof FrameLayout) {
            final FrameLayout frameParent = (FrameLayout) view;//整个父布局
            final LinearLayout linearLayout = new LinearLayout(this){
                @Override
                public boolean dispatchTouchEvent(MotionEvent ev) {
                    super.dispatchTouchEvent(ev);
                    return false;
                }
            };
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setBackgroundColor(Color.parseColor("#88000000"));//背景设置灰色透明
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    frameParent.removeView(linearLayout);
                }
            });
//            linearLayout.requestDisallowInterceptTouchEvent(true);
            //防止点击穿透问题
//            linearLayout.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    return false;
//                }
//            });
//            Rect rect = new Rect();
//            Point point = new Point();
////    nearby.getGlobalVisibleRect(rect, point);
////获得nearby这个控件的宽高以及XY坐标 nearby这个控件对应就是需要高亮显示的地方
//            ImageView topGuideview = new ImageView(this);
//            topGuideview.setLayoutParams(new ViewGroup.LayoutParams(rect.width(), rect.height()));
////     topGuideview.setBackgroundResource(R.drawable.iv_topguide);
//            Rect rt = new Rect();
//            getWindow().getDecorView().getWindowVisibleDisplayFrame(rt);
//            topGuideview.setY(point.y - rt.top);//rt.top是手机状态栏的高度
//            ImageView bottomGuideview = new ImageView(this);
//            bottomGuideview.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
////    bottomGuideview.setBackgroundResource(R.drawable.iv_bottomguide);
//            bottomGuideview.setY(point.y + topGuideview.getHeight());
//            linearLayout.addView(topGuideview);
//            linearLayout.addView(bottomGuideview);
            frameParent.addView(linearLayout);
            ImageView topGuideview = new ImageView(this);
            topGuideview.setLayoutParams(new ViewGroup.LayoutParams(100, 200));
            topGuideview.setBackgroundResource(R.mipmap.home);
            linearLayout.addView(topGuideview);
        }
    }
}