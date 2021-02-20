package com.view.recyclerview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by houyongliang on 2017/5/16.
 * 待定
 */

public class RefreshRecyclerView  extends RecyclerView {

    private final Context mContext;

    //触摸事件中按下的Y坐标，初始值为-1，为防止ACTION_DOWN事件被抢占
    private float startY = -1;
    //    下拉刷新控件的高度
    private int mHeaderViewHeight;
    //    刷新状态：下拉刷新
    private final int PULL_DOWN_REFRESH = 0;
    //    刷新状态：释放刷新
    private final int RELEASE_REFRESH = 1;
    //    刷新状态：正常刷新
    private final int REFRESHING = 2;

    //    当前头布局的状态-默认为下拉刷新
    private int currState = PULL_DOWN_REFRESH;
    //
    private RefreshRecyclerView.OnRefreshListener mOnRefreshListener;

    //    尾部试图（上拉加载控件）的高度
    private int footerViewHeight;
    //    判断是否是加载更多
    private boolean isLoadingMore;
    private LinearLayout mHeaderViewContent;
    private View mFooterView;
//    private XListViewHeader mHeaderView;
    private boolean mEnablePullRefresh;
   private  boolean mPullRefreshing;
    private ImageView mArrowImageView;
    private TextView mHintTextView;
    private RotateAnimation upAnima;
    private RotateAnimation downAnima;
    private View mHeaderView;
    private ObjectAnimator anim;

    public RefreshRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initHeaderView();
        initFooterView();
    }

    /**
     * 通过HeaderAndFooterWrapper对象给RecyclerView添加尾部
     * @param footerView 尾部视图
     * @param headerAndFooterWrapper RecyclerView.Adapter的包装类对象，通过它给RecyclerView添加尾部视图
     */
    public void addFooterView(View footerView, HeaderAndFooterWrapper headerAndFooterWrapper) {
        headerAndFooterWrapper.addFootView(footerView);
    }
    /**
     * 返回尾部布局，供外部调用
     * @return
     */
    public View getFooterView(){
        return mFooterView;
    }
    /**
     * 返回头部布局，供外部调用
     * @return
     */
    public View getHeaderView(){
        return mHeaderView;
    }


    /**
     * 通过HeaderAndFooterWrapper对象给RecyclerView添加头部部
     * @param headerView 尾部视图
     * @param headerAndFooterWrapper RecyclerView.Adapter的包装类对象，通过它给RecyclerView添加头部视图
     */
    public void addHeaderView(View headerView, HeaderAndFooterWrapper headerAndFooterWrapper) {
        headerAndFooterWrapper.addHeaderView(headerView);
    }
    private void initHeaderView() {
//        mHeaderView=    LayoutInflater.from(mContext).inflate(R.layout.xlistview_header, null, false);
//        XListViewHeader xListViewHeader=new XListViewHeader(mContext);
//        mHeaderView =  View.inflate(mContext, R.layout.xlistview_header, null);
//        mHeaderViewContent = (LinearLayout) mHeaderView.findViewById(R.id.xlistview_header_content);
//        mArrowImageView = (ImageView) mHeaderView.findViewById(R.id.xlistview_header_arrow);
//        mHintTextView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_hint_textview);
//        mHeaderView.measure(0, 0);
//       if(mHeaderView.getMeasuredWidth()!=
//        ScreenUtils.getScreenWidth(mContext)){
//           Toast.makeText(mContext, "mHeaderView.getMeasuredWidth()!==\n" +
//                   "        ScreenUtils.getScreenWidth(mContext)", Toast.LENGTH_SHORT).show();
//           Toast.makeText(mContext, mHeaderView.getMeasuredWidth()+"mHeaderView.getMeasuredWidth()", Toast.LENGTH_SHORT).show();
//           Toast.makeText(mContext,  ScreenUtils.getScreenWidth(mContext)+" ScreenUtils.getScreenWidth(mContext)", Toast.LENGTH_SHORT).show();
//       }

        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
//        mHeaderView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, mHeaderViewHeight));
        // init header height
//        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @SuppressWarnings("deprecation")
//                    @Override
//                    public void onGlobalLayout() {
//                        mHeaderViewHeight = mHeaderViewContent.getHeight();
//                        getViewTreeObserver()
//                                .removeGlobalOnLayoutListener(this);
//                    }
//                });
        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
        //初始化头部布局的动画



    }

    private void initAnimation(final View view, int duration) {
        //
//
//
        anim = ObjectAnimator//
                .ofFloat(view, "alpha", 1.0F,  0.0F);//
//                .setDuration(duration);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float cVal = (Float) animation.getAnimatedValue();
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            }
        });


    }


    private void initFooterView() {
//        mFooterView=    LayoutInflater.from(mContext).inflate(R.layout.xlistview_footer, null, false);
//
//        mFooterView.measure(0, 0);
//        //得到控件的高
//        footerViewHeight = mFooterView.getMeasuredHeight();
//        //默认隐藏下拉刷新控件
//        // View.setPadding(0,-控件高，0,0);//完全隐藏
//        //View.setPadding(0, 0，0,0);//完全显示
//        mFooterView.setPadding(0, -footerViewHeight, 0, 0);
//        addFooterView(footerView);

            this.addOnScrollListener(new MyOnScrollListener());
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //防止ACTION_DOWN事件被抢占，没有执行
                if (startY == -1) {
                    startY = ev.getY();
                }
                float endY = ev.getY();
                float dY = endY - startY;
                //判断当前是否正在刷新中
                if (currState == REFRESHING) {
                    //如果当前是正在刷新，不执行下拉刷新了，直接break;
                    break;
                }
//                如果是下拉
                if (dY > 0) {
                    int paddingTop = (int) (dY - mHeaderViewHeight);
                    if (paddingTop > 0 && currState != RELEASE_REFRESH) {
                        //完全显示下拉刷新控件，进入松开刷新状态
//                        mHeaderView.setVisibility(VISIBLE);
                        currState = RELEASE_REFRESH;
                        refreshViewState();
                    } else if (paddingTop < 0 && currState != PULL_DOWN_REFRESH) {
                        //没有完全显示下拉刷新控件，进入下拉刷新状态
                        currState = PULL_DOWN_REFRESH;
                        refreshViewState();
                    }

                    mHeaderView.setPadding(0, paddingTop, 0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                //5.重新记录值
                startY = -1;
                if (currState == PULL_DOWN_REFRESH) {
                    //设置默认隐藏
                    mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
                } else if (currState == RELEASE_REFRESH) {
                    //当前是释放刷新，进入到正在刷新状态，完全显示
                    currState = REFRESHING;
                    refreshViewState();
                    mHeaderView.setPadding(0, 0, 0, 0);
                    //调用用户的回调事件，刷新页面数据
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onPullDownRefresh();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    /**
     * 跳转刷新状态
     */
    private void refreshViewState() {
        switch (currState) {
//            跳转到下拉刷新
            case PULL_DOWN_REFRESH:
//                mArrowImageView.startAnimation(downAnima);
//                initAnimation(mHeaderView,0);
//                mHintTextView.setText(R.string.xlistview_header_hint_normal);

                break;
//            跳转到释放刷新
            case RELEASE_REFRESH:
//                mArrowImageView.startAnimation(downAnima);
//                mHintTextView.setText(R.string.xlistview_header_hint_ready);
                break;
//            跳转到正在刷新
            case REFRESHING:

//                mArrowImageView.setImageResource(R.drawable.frame_xlistview_loading);
//                AnimationDrawable animationDrawable = (AnimationDrawable) mArrowImageView.getDrawable();
//                animationDrawable.start();
//                mHintTextView.setText("正在刷新..");
                break;
        }
    }
    /**
     * 定义下拉刷新和上拉加载的接口
     */
    public interface OnRefreshListener {
        /**
         * 当下拉刷新时触发此方法
         */
        void onPullDownRefresh();/**/

        /**
         * 当加载更多的时候回调这个方法
         */
        void onLoadingMore();

    }
    public void setOnRefreshListener(RefreshRecyclerView.OnRefreshListener listener) {
        this.mOnRefreshListener = listener;
    }

    /**
     * 当刷新完数据之后，调用此方法，把头文件隐藏，并且状态设置为初始状态
     * @param isSuccess
     */
    public void onFinishRefresh(boolean isSuccess) {
        if (isLoadingMore) {
            mFooterView.setPadding(0, -footerViewHeight, 0, 0);
            isLoadingMore = false;
        } else {
            mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
            currState = PULL_DOWN_REFRESH;
//            mArrowImageView.setVisibility(VISIBLE);
//            pb_header_refresh.setVisibility(GONE);
            mHintTextView.setText("下拉刷新");
            if (isSuccess) {
                //设置更新时间
//                tv_time.setText(getSystemTime());
            }
        }
    }

    private String getSystemTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }


    private class MyOnScrollListener extends OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
               super.onScrollStateChanged(recyclerView, newState);
            if (newState == SCROLL_STATE_IDLE || newState == SCROLL_STATE_SETTLING) {
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    //当停止滚动时或者惯性滚动时，RecyclerView的最后一个显示的条目：getCount()-1
                    //              注意是findLastVisibleItemPosition()而不是getLastVisiblePosition
                    if (linearLayoutManager.findLastVisibleItemPosition() >= getChildCount() - 1) {
                        isLoadingMore = true;
                        //把底部加载显示
                        mFooterView.setPadding(0, 0, 0, 0);
                        if (mOnRefreshListener != null) {
                            mOnRefreshListener.onLoadingMore();
                        }
                    }
                }
            }
        }
    }
//    /**
//     * enable or disable pull down refresh feature.
//     *
//     * @param enable
//     */
//    public void setPullRefreshEnable(boolean enable) {
//        mEnablePullRefresh = enable;
//        if (!mEnablePullRefresh) { // disable, hide the content
//            mHeaderViewContent.setVisibility(View.INVISIBLE);
//        } else {
//            mHeaderViewContent.setVisibility(View.VISIBLE);
//        }
//    }
//
//    /**
//     * stop refresh, reset header view.
//     */
//    public void stopRefresh() {
//        if ( mPullRefreshing == true) {
//            mPullRefreshing = false;
////            resetHeaderHeight();
//        }
//    }


}
