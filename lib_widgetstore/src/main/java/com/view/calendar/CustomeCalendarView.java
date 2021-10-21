package com.view.calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.utils.ScreenUtils;
import com.yongliang.widgetstore.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 */
public class CustomeCalendarView extends View {

    private String LOG_TAG = "CustomerCalendarView";
    // 列的数量
    private static final int NUM_COLUMNS = 7;
    // 行的数量
    private static final int NUM_ROWS = 7;
    /**
     * 点击日期
     */
    private String mOnClickDate = "";

    // 整个日历背景颜色
    private int mCalendarBgColor = Color.parseColor("#ffffff");
    // 日历头文字颜色
    private int mCalendarHeaderTextColor = Color.parseColor("#999999");
    // 当月日期文字默认颜色
    private int mCalendarDayNormalTextColor = Color.parseColor("#333333");
    // 当月日期文字点击颜色
    private int mCalendarDayClickTextColor = Color.parseColor("#ffffff");
    // 非本月日期背景颜色
    private int mCalendarOtherMonthBgColor = Color.parseColor("#f6f6f6");
    // 非本月日期文字颜色
    private int mCalendarOtherMonthTextColor = Color.parseColor("#CCCCCC");
    // 当月日期背景默认颜色
    private int mCalendarBgNormalColor = Color.parseColor("#ffffff");
    // 当月法定节假日文字颜色
    private int mCalendarHolidayTextColor = Color.parseColor("#ff5722");
    //当天背景颜色
    private int mTodayTextColor= Color.parseColor("#33ff5722");
    // 销售的文字画笔
    private int mCalendarSaleTextColor = Color.parseColor("#cc6600");
    private int mCalendarSaleCircleColor = Color.parseColor("#fcefd3");
    // 天数默认字体大小
    private int mDayNormalTextSize = getResources().getInteger(R.integer.renewal_attendance_calendar_day_text_size);
    // 班休字体大小
    private int mStateTextSize = getResources().getInteger(R.integer.renewal_attendance_calendar_state_text_size);
    //销售线索字体大小 默认为9
    private int mSaleTextSize = getResources().getInteger(R.integer.renewal_attendance_calendar_state_sale_text_size);

    private DisplayMetrics mMetrics;
    private Paint mBackgroundPaint,mHeaderPaint;
    private Paint mLastMonthBgPaint, mLastMonthTextPaint;
    private Paint mThisMonthTextPaint;
    private Paint mCirclePaint;
    private Paint mTodayPaint;
    private Paint mClickPaint;
    private Paint mSalePaint;/*定义 销售的文字画笔*/
    private Paint mSaleCirclePaint;/*定义 销售的文字背景圆圈画笔*/
    private int mCurYear;
    private int mCurMonth;
    private int mCurDate;

    private int mSelYear;
    private int mSelMonth;
    private int mSelDate;
    private int mColumnSize;
    private int mRowSize;
    private int[][] mDays = new int[7][7];
    private String[] week = {"日", "一", "二", "三", "四", "五", "六"};
    private int mToday;
    // 当月一共有多少天
    private int mMonthDays;
    // 当月第一天位于周几
    private int mWeekNumber;


    private int itemSize;
    private int lastMonthCount;

    private int afterMonthDays;
    private List<String> afterMonDays = new ArrayList<>();
    private Integer mDesiredHeight;
    private int downX;
    private int downY;
    private boolean mClickable = true;
    private Map map=new HashMap();
    private int j;
    private boolean isClickMonth;
    private List<String> mPointData=new ArrayList<>();
    private List<String> mSaleData=new ArrayList<>();/*销售进程*/
    private String mMark="销";//右上角的显示字段
    public CustomeCalendarView(Context context) {
        super(context);
        init();
    }

    public CustomeCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomeCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        // 获取手机屏幕参数
        mMetrics = getResources().getDisplayMetrics();

        // 创建日历背景画笔
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(mCalendarBgColor);
        // 创建日历头画笔
        mHeaderPaint = new Paint();
        mHeaderPaint.setAntiAlias(true);
        mHeaderPaint.setTextSize(mDayNormalTextSize * mMetrics.scaledDensity);
        mHeaderPaint.setColor(mCalendarHeaderTextColor);
        // 创建日历上月日期背景画笔
        mLastMonthBgPaint = new Paint();
        mLastMonthBgPaint.setAntiAlias(true);//为画笔设置抗锯齿，这样做的作用就是让图像边缘可以相对清晰一点
        mLastMonthBgPaint.setColor(mCalendarOtherMonthBgColor);//设置背景颜色
        mLastMonthBgPaint.setStyle(Paint.Style.FILL);//绘图为线条模式
        // 创建日历上月日期文字画笔
        mLastMonthTextPaint = new Paint();
        mLastMonthTextPaint.setAntiAlias(true);
        mLastMonthTextPaint.setTextSize(mDayNormalTextSize * mMetrics.scaledDensity);
        mLastMonthTextPaint.setColor(mCalendarOtherMonthTextColor);
        // 创建日历当月日期文字画笔
        mThisMonthTextPaint = new Paint();
        mThisMonthTextPaint.setAntiAlias(true);
        mThisMonthTextPaint.setTextSize(mDayNormalTextSize * mMetrics.scaledDensity);
        mThisMonthTextPaint.setStyle(Paint.Style.FILL);
        //系统消息小红点
        mCirclePaint=new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCalendarHolidayTextColor);
        mCirclePaint.setStyle(Paint.Style.FILL);
        //当天背景画笔
        mTodayPaint=new Paint();
        mTodayPaint.setAntiAlias(true);
        mTodayPaint.setColor(mTodayTextColor);
        mTodayPaint.setStyle(Paint.Style.FILL);//绘图为填充模式
        //点击后背景颜色
        mClickPaint=new Paint();
        mClickPaint.setAntiAlias(true);
        mClickPaint.setColor(mCalendarHolidayTextColor);
        mClickPaint.setStyle(Paint.Style.FILL);//绘图为填充模式
        //
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        mCurYear = calendar.get(Calendar.YEAR);
        mCurMonth = calendar.get(Calendar.MONTH);
        mCurDate = calendar.get(Calendar.DATE);
        setCalendarDate(mCurYear, mCurMonth, mCurDate);

        //系统销售线索
        mSalePaint=new Paint();
        mSalePaint.setAntiAlias(true);
        mSalePaint.setColor(mCalendarSaleTextColor);
        mSalePaint.setStyle(Paint.Style.STROKE);
        mSalePaint.setTextSize(mSaleTextSize * mMetrics.scaledDensity);//设置文字大小
        //系统销售线索
        mSaleCirclePaint=new Paint();
        mSaleCirclePaint.setAntiAlias(true);
        mSaleCirclePaint.setColor(mCalendarSaleCircleColor);
        mSaleCirclePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(LOG_TAG, "屏幕widthMeasureSpec=" + widthMeasureSpec);
        int width = this.getMeasuredSize(widthMeasureSpec);
        Log.i(LOG_TAG, "width=" + width);
        //int height = this.getMeasuredSize(heightMeasureSpec);
        setMeasuredDimension(width,getHeightMeasuredSize(widthMeasureSpec));
    }

    private int getMeasuredSize(int widthMeasureSpec) {
        //模式
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        //尺寸
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        //计算所得的实际尺寸，要被返回
        int retSize = 0;
        //得到两侧的留边
        int padding =(getPaddingLeft() + getPaddingRight());
        //对不同模式进行判断
        if(specMode == MeasureSpec.EXACTLY){//显示指定控件大小
            retSize = specSize;
        }else{
            retSize = (getWidth() + padding);
            if(specMode== MeasureSpec.UNSPECIFIED){
                retSize = Math.min(retSize,specSize);
            }
        }
        return retSize;
    }
    private int getHeightMeasuredSize(int heightMeasureSpec) {
        //模式
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        //尺寸
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        //计算所得的实际尺寸，要被返回
        int retSize = 0;
        //得到两侧的留边
        int padding =(getPaddingLeft() + getPaddingRight());
        //对不同模式进行判断
        if(specMode == MeasureSpec.EXACTLY){//显示指定控件大小
            retSize = specSize+ ScreenUtils.dpToPx(10);
        }else{
            retSize = (getWidth() + padding);
            if(specMode== MeasureSpec.UNSPECIFIED){
                retSize = Math.min(retSize,specSize);
            }
        }
        return retSize;
    }
    public void setData(List<String> list){
        if(list==null||list.size()==0)
            return;
        this.mPointData=list;
        invalidate();
    }
    public void setSaleData(List<String> list){
        if(list==null||list.size()==0)
            return;
        this.mSaleData=list;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
      /*  if(null == mMoudleList || 0 == mMoudleList.size()){
            return;
        }*/

        onDrawCalendarPre();

        // 绘制日历背景
        drawCalendarBackground(canvas, mBackgroundPaint);
        // 绘制日历头（周日到周一）
        drawCalendarHeader(canvas, mHeaderPaint);
        // 绘制上一个月日期
        drawCalendarLastMonth(canvas, mLastMonthBgPaint, mLastMonthTextPaint);
        // 绘制当月和下月日期
        drawCalendarThisAndNextMonth(canvas);
    }

    private void onDrawCalendarPre() {
        // 获取当月一共有多少天
        mMonthDays = MyDateUtils.getMonthCount(mSelYear, mSelMonth);
        // 获取当月第一天位于周几
        mWeekNumber = MyDateUtils.getFirstDayWeek(mSelYear, mSelMonth) - 1;
        if(0 == mWeekNumber){
            // 如果是周日，补齐7天
            mWeekNumber = 7;
        }
//        MyLog.wtf(LOG_TAG, "mWeekNumber=" + mWeekNumber);
    }

    private void drawCalendarBackground(Canvas canvas, Paint paint) {
        initResources();
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
    }


    /**
     * 初始化列宽，高以及背景
     */
    private void initResources() {
        // 初始化每列的大小
        mColumnSize = getWidth() / NUM_COLUMNS;
        // 初始化每行的大小
        mRowSize = getHeight() / NUM_ROWS;
        // 获取背景Bitmap
        if (getWidth() < getHeight()) {
            itemSize = getWidth() / 7;
        } else {
            itemSize = getHeight() / 7;
        }
    }

    private void drawCalendarHeader(Canvas canvas, Paint paint) {

        for (int i = 0; i < 7; i++) {
            //canvas.drawBitmap(mCalendarHeaderBgBitmap, itemSize * i, 0, paint);

            // 绘制日历头文字
            float textWidth = paint.measureText(week[i]);
            float x = itemSize / 2 - textWidth / 2;
            Paint.FontMetrics metrics = paint.getFontMetrics();
            //metrics.ascent为负数
            float dy = -(metrics.descent + metrics.ascent) / 2;
            float y = itemSize / 2 + dy;
            canvas.drawText(week[i], itemSize * i + x, 0 + y, paint);
//            MyLog.wtf(LOG_TAG, "日历头：i=" + i);
//            MyLog.wtf(LOG_TAG, "日历头：week[i]=" + week[i]);
        }
    }

    private void drawCalendarLastMonth(Canvas canvas, Paint lastMonthBgPaint, Paint lastMonthTextPaint) {
        int lastMonthYear,lastMonthYue;
        if (mSelMonth == 0) {
            lastMonthYear = mSelYear - 1;
            lastMonthYue = 11;
        } else {
            lastMonthYear = mSelYear;
            lastMonthYue = mSelMonth - 1;
        }
//        MyLog.wtf(LOG_TAG, "上个月是：" + lastMonthYear + "年" + (lastMonthYue + 1) + "月");
        lastMonthCount = MyDateUtils.getMonthCount(lastMonthYear, lastMonthYue);// 上月日子总数
//        MyLog.wtf(LOG_TAG, "上个月日子总数：lastMonthCount=" + lastMonthCount);
        // 计算上个月要显示的日期
        int lastMonthRi;
        String lastMonthRiStr, lastMonthDate;
        for (int i = 0; i < mWeekNumber; i++) {
            lastMonthRi = lastMonthCount - mWeekNumber + i + 1;// 日期（只有日）
//            MyLog.wtf(LOG_TAG, "上个月：lastMonthRi=" + lastMonthRi);
            lastMonthRiStr = lastMonthRi + "";
            lastMonthDate = getCalendarData(lastMonthYear, lastMonthYue, lastMonthRi);
//            MyLog.wtf(LOG_TAG, "上个月：lastMonthDate=" + lastMonthDate);
            if(map.size()<42){
                map.put(i, Integer.parseInt(lastMonthRiStr));
                j++;
            }
            // 绘制上个月要显示的日期
            int startX = (int) (itemSize * (i));
            // 绘制上月日期文字
            float textWidth = lastMonthTextPaint.measureText(lastMonthRiStr);
            float x = itemSize / 2 - textWidth / 2;
            Paint.FontMetrics metrics = lastMonthTextPaint.getFontMetrics();
            //metrics.ascent为负数
            float dy = -(metrics.descent + metrics.ascent) / 2;
            mClickPaint.setColor(mCalendarOtherMonthTextColor);
            mThisMonthTextPaint.setColor(mCalendarOtherMonthTextColor);
            if(lastMonthDate.equals(mOnClickDate)){
                mClickPaint.setColor(mCalendarHolidayTextColor);
                mThisMonthTextPaint.setColor(mCalendarDayClickTextColor);
            }else{
                mClickPaint.setColor(mCalendarDayClickTextColor);
                mThisMonthTextPaint.setColor(mCalendarOtherMonthTextColor);
            }
            for(String date:mPointData){
                if(date.equals(lastMonthDate)){
                    canvas.drawCircle(startX+itemSize/2,itemSize*2,getResources().getInteger(R.integer.qutuo_red_circle_size),mCirclePaint);
                    break;
                }
            }
            canvas.drawCircle(startX + itemSize / 2, itemSize*3/2, itemSize / 2 * 2 / 3, mClickPaint);
           // canvas.drawCircle(startX + itemSize*3/4, itemSize + itemSize/4, itemSize/4*3/5,  mClickPaint);
            canvas.drawText(lastMonthRiStr, startX + x, itemSize + itemSize / 2 + dy, mThisMonthTextPaint);

            //测试 销售
            float textSaleWidth = mSalePaint.measureText(mMark);
            float  textSaleHeight = mSalePaint.getFontMetrics().descent- mSalePaint.getFontMetrics().ascent;
            float xSale =itemSize / 2+ textWidth / 2+ textSaleWidth / 2;
            Paint.FontMetrics metricsSale = mSalePaint.getFontMetrics();
            //metrics.ascent为负数
            float dySale = Math.abs((metricsSale.descent + metricsSale.ascent) );
            for(String date:mSaleData){
                if(date.equals(lastMonthDate)){
                    float ss=(float)((mSaleTextSize+2.0)* mMetrics.scaledDensity/2);
                    canvas.drawCircle(startX+xSale+textSaleWidth/2,itemSize+itemSize / 2+dy-dySale-dySale/2,ss,mSaleCirclePaint);
                    canvas.drawText(mMark,startX+xSale,itemSize+itemSize / 2+dy-dySale,mSalePaint);

                    break;
                }
            }

        }
    }

    private void drawCalendarThisAndNextMonth(Canvas canvas) {
        // 设置绘制字体内容
        String dayStr;
        //计算当月有几周
        int weeks = MyDateUtils.getWeeks(mSelYear, mSelMonth);
        //书写当月日期
        int i = 1;
        if (weeks <= 6){
            weeks = 6;
        }else{
            try {
                throw new Exception("月份计算错误");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int day = 0; day < 7 * weeks - mWeekNumber; day++) {
            dayStr = String.valueOf(day + 1);
            int column = (day + mWeekNumber) % 7;//列
            int row = (day + mWeekNumber) / 7;// 行
            mDays[row+1][column] = day + 1;

//            MyLog.wtf(LOG_TAG, "当月和下月：day=" + day);

            int startX = (int) (itemSize * (column));
            int startY = (int) (itemSize * (row + 1));
            // -------------------- 下月日期 -----------------
            // 计算下一个月的日期mMonthDays是当月天数
            if (day > mMonthDays - 1) {
//                MyLog.wtf(LOG_TAG, "下月：day=" + day);
                String date = getCalendarData(mSelYear, mSelMonth+1, i);
//                MyLog.wtf(LOG_TAG, "下月：date=" + date);
                if(mSelMonth+1==13){
                    date=getCalendarData(mSelYear+1,1,i);
                }
                // 绘制下月日期背景
                //canvas.drawBitmap(mOtherMonthBgBitmap, startX, startY, mPaint);
                canvas.drawCircle(startX + itemSize/2, startY + itemSize/2, itemSize/2*2/3, mLastMonthBgPaint);
                if(map.size()<42){
                    map.put(j+mMonthDays+i-1,i);
                }
                // 绘制下月日期文字
                float textWidth = mLastMonthTextPaint.measureText(i + "");
                float x = itemSize / 2 - textWidth / 2;
                Paint.FontMetrics metrics = mLastMonthTextPaint.getFontMetrics();
                //metrics.ascent为负数
                float dy = -(metrics.descent + metrics.ascent) / 2;
                canvas.drawText(i + "", startX + x, startY + itemSize / 2 + dy, mLastMonthTextPaint);
                mClickPaint.setColor(mCalendarDayClickTextColor);
                mThisMonthTextPaint.setColor(mCalendarOtherMonthTextColor);
                if(date.equals(mOnClickDate)){
                    mClickPaint.setColor(mCalendarHolidayTextColor);
                    mThisMonthTextPaint.setColor(mCalendarDayClickTextColor);
                }else{
                    mClickPaint.setColor(mCalendarDayClickTextColor);
                    mThisMonthTextPaint.setColor(mCalendarOtherMonthTextColor);
                }
                for(String str:mPointData){
                    if(str.equals(date)){
                        canvas.drawCircle(startX+itemSize/2,startY+itemSize,getResources().getInteger(R.integer.qutuo_red_circle_size),mCirclePaint);
                        break;
                    }
                }



                canvas.drawCircle(startX + itemSize / 2, startY + itemSize / 2, itemSize / 2 * 2 / 3, mClickPaint);
                canvas.drawText(i + "", startX + x, startY + itemSize / 2 + dy, mThisMonthTextPaint);

                //测试 销售
                float textSaleWidth = mSalePaint.measureText(mMark);
               float  textSaleHeight = mSalePaint.getFontMetrics().descent- mSalePaint.getFontMetrics().ascent;
                float xSale =itemSize / 2+ textWidth / 2+ textSaleWidth / 2;
                Paint.FontMetrics metricsSale = mSalePaint.getFontMetrics();
                //metrics.ascent为负数
                float dySale = Math.abs((metricsSale.descent + metricsSale.ascent));
                for(String str:mSaleData){
                    if(str.equals(date)){
                        float ss=(float)((mSaleTextSize+2.0)* mMetrics.scaledDensity/2);
                        canvas.drawCircle(startX+xSale+textSaleWidth/2,startY+itemSize / 2+dy-dySale-dySale/2,ss,mSaleCirclePaint);
                        canvas.drawText(mMark,startX+xSale,startY+itemSize / 2+dy-dySale,mSalePaint);
                        break;
                    }

                }

                i++;
            } else {// ---------------- 当月日期 -----------------
                String date = getCalendarData(mSelYear, mSelMonth, mDays[row + 1][column]);
                if(map.size()<42){
                    map.put(day+j,day+1);
                }
                // 绘制日期
                float textWidth = mThisMonthTextPaint.measureText(dayStr);
                float x = itemSize / 2 - textWidth / 2;
                Paint.FontMetrics metrics = mThisMonthTextPaint.getFontMetrics();
                //metrics.ascent为负数
                float dy = -(metrics.descent + metrics.ascent) / 2;
                if(date.equals(mOnClickDate)){
                    mClickPaint.setColor(mCalendarHolidayTextColor);
                    mThisMonthTextPaint.setColor(mCalendarDayClickTextColor);
                }else{
                    mClickPaint.setColor(mCalendarBgNormalColor);
                    mThisMonthTextPaint.setColor(mCalendarDayNormalTextColor);
                }
                for(String str:mPointData){
                    if(str.equals(date)){
                        canvas.drawCircle(startX+itemSize/2,startY+itemSize,getResources().getInteger(R.integer.qutuo_red_circle_size),mCirclePaint);
                        break;
                    }
                }





                canvas.drawCircle(startX + itemSize / 2, startY + itemSize / 2, itemSize / 2 * 2 / 3, mClickPaint);
                canvas.drawText(dayStr, startX + x, startY + itemSize / 2 + dy, mThisMonthTextPaint);
                //测试 销售
                float textSaleWidth = mSalePaint.measureText(mMark);
                float  textSaleHeight = mSalePaint.getFontMetrics().descent- mSalePaint.getFontMetrics().ascent;
                float xSale =itemSize / 2+ textWidth / 2+ textSaleWidth / 2;
                Paint.FontMetrics metricsSale = mSalePaint.getFontMetrics();
                //metrics.ascent为负数
                float dySale = Math.abs((metricsSale.descent + metricsSale.ascent) );
                for(String str:mSaleData){
                    if(str.equals(date)){
                        float ss=(float)((mSaleTextSize+2.0)* mMetrics.scaledDensity/2);
                        canvas.drawCircle(startX+xSale+textSaleWidth/2,startY+itemSize / 2+dy-dySale-dySale/2,ss,mSaleCirclePaint);
                        canvas.drawText(mMark,startX+xSale,startY+itemSize / 2+dy-dySale,mSalePaint);
                        break;
                    }

                }
                if(day==mToday-1){
                    canvas.drawCircle(startX + itemSize / 2, startY + itemSize / 2, itemSize / 2 * 2 / 3, mTodayPaint);
                }
            }
        }
    }
public void setTodayDate(int date){
    mToday=date;
}
    /**
     * 设置日历控件的年月日
     *
     * @param year  年
     * @param month 月
     * @param date  日
     */
    private void setCalendarDate(int year, int month, int date) {
        this.mSelYear = year;
        this.mSelMonth = month;
        this.mSelDate = date;
    }

    /**
     * 跳转至指定日期
     *
     * @param year
     * @param month
     * @param date
     */
    public void skipToDate(int year, int month, int date) {
        mOnClickDate = "";
        map.clear();
        j=0;
        mToday=0;
        setCalendarDate(year, month, date);
    }

    /**
     * 获取当前展示的日期
     *
     * @return 格式：20160606
     */
    private String getCalendarData(int year, int month, int date) {
        String monty, day;
        month = (month + 1);

        // 判断月份是否有非0情况
        if ((month) < 10) {
            monty = "0" + month;
        } else {
            monty = String.valueOf(month);
        }

        // 判断天数是否有非0情况
        if ((date) < 10) {
            day = "0" + (date);
        } else {
            day = String.valueOf(date);
        }
        return year + monty + day;
    }
   /* public void setClickDate(String clickDate){
        this.mOnClickDate=clickDate;
        invalidate();
    }*/
    public String getClickDate(int row, int column){
        if(row==7||!map.containsKey((row-1)*7+column))
            return mOnClickDate;
        int value= (int) map.get((row-1)*7+column);
       if(row==1){
           if(value>=15) {
               if(mSelMonth==0){
                   return getDate(mSelYear-1,12,value);
               }
               return getDate(mSelYear,mSelMonth,value);
           }else{
               return getDate(mSelYear,mSelMonth+1,value);
           }
       }
       if(row==5||row==6 ){
           if(value<=15){
               if(mSelMonth==11){
                   return getDate(mSelYear+1,1,value);
               }
              return getDate(mSelYear,mSelMonth+2,value);
           }else{
              return getDate(mSelYear,mSelMonth+1,value);
           }
       }
       return getDate(mSelYear,mSelMonth+1,value);
    }
    /**
     * 获取当前展示的日期
     *
     * @return 格式：20160606
     */
    private String getDate(int year, int month, int date) {
        String monty, day;
        // 判断月份是否有非0情况
        if ((month) < 10) {
            monty = "0" + month;
        } else {
            monty = String.valueOf(month);
        }

        // 判断天数是否有非0情况
        if ((date) < 10) {
            day = "0" + (date);
        } else {
            day = String.valueOf(date);
        }
        return year + monty + day;
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        /*recyclerBitmap(mOtherMonthBgBitmap);
        recyclerBitmap(mThisMonthChuQinBgBitmap);
        recyclerBitmap(mThisMonthChuQinClickBgBitmap);
        recyclerBitmap(mThisMonthQueQinBgBitmap);
        recyclerBitmap(mThisMonthQueQinBgBitmap);
        recyclerBitmap(mThisMonthWeekendBgBitmap);*/
    }

    /**
     * 释放Bitmap资源
     */
    private void recyclerBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
    /**
     * 设置日历是否可以点击
     */
    @Override
    public void setClickable(boolean clickable) {
        this.mClickable = clickable;
    }

    /**
     * 重写事件
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventCode = event.getAction();
        switch(eventCode){
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(!mClickable) {
                    return true;
                }

                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if(Math.abs(upX - downX) < 10 && Math.abs(upY - downY) < 10){
                    performClick();
                    onDateClick((upX + downX) / 2, (upY + downY) / 2);
                }
                break;
        }
        return true;
    }
    /**
     * 点击事件
     */
    private void onDateClick(int x, int y){
        int row = y / itemSize;
        int column = x / itemSize;
        int i = (row-1)*7 + column;// 在mMoudleList中的index（需要注意：row，colum，i都是从0开始）
//        MyLog.wtf(LOG_TAG, "点击事件：row=" +row +"; column="+column +"; i="+i);

        if(mListener != null){
           /* if(row==0){
                row=1;
            }*/
            /*if(row==7){
                row=6;
            }
            if(column==7){
                column=6;
            }*/
       //    mListener.onClickDateListener(mSelYear, (mSelMonth + 1), mDays[row][column]);
//            MyLog.wtf(LOG_TAG, "点击事件：mSelYear=" +mSelYear +"; mSelMonth="+mSelMonth +"; mSelDay="+mDays[row][column]);
            //mOnClickDate = getCalendarData(mSelYear, mSelMonth, mDays[row][column]);
            mOnClickDate=getClickDate(row,column);
            mListener.onClickDateListener(mOnClickDate);
        }
        invalidate();
    }

    private OnDateSelectedClickListener mListener;

    public interface OnDateSelectedClickListener {
        void onClickDateListener(String date);
    }

    /**
     * 点击事件监听
     * @param mListener
     */
    public void setOnDateSelectedListener(OnDateSelectedClickListener mListener) {
        this.mListener = mListener;
    }
}
