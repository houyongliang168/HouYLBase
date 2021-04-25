package com.yongliang.houylbase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yongliang.houylbase.utils.BlankFragment;

import static androidx.constraintlayout.widget.ConstraintProperties.PARENT_ID;


public class TestComposeViewActivity extends AppCompatActivity {

    private LinearLayout ll_contain;
    private ConstraintLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_compose_view);
        ll_contain = findViewById(R.id.ll_contain);
        cl = findViewById(R.id.cl);
        initView();

    }

    private void initView() {
        FragmentManager fragmentManager=getSupportFragmentManager();

        FragmentTransaction ft= fragmentManager.beginTransaction();
        ft.add(R.id.ll_contain, BlankFragment.newInstance());
        ft.commitAllowingStateLoss();

        View mErrorView = LayoutInflater.from (this).inflate (R.layout.page_error, null);
        ConstraintLayout.LayoutParams errorLayoutParams = new ConstraintLayout .LayoutParams (
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        errorLayoutParams.topToTop=PARENT_ID;
        errorLayoutParams.leftToLeft=PARENT_ID;
        errorLayoutParams.rightToRight=PARENT_ID;
        errorLayoutParams.bottomToBottom=PARENT_ID;
        mErrorView.setLayoutParams (errorLayoutParams);
        cl.addView(mErrorView);

    }
}