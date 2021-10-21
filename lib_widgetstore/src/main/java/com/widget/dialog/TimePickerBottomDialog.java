package com.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.time_picker.TimePickerView;
import com.time_picker.listener.CustomListener;
import com.yongliang.widgetstore.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 建立统一的 dialog  从下弹框
 * Created by 41113 on 2018/9/19.
 */

public class TimePickerBottomDialog extends Dialog {


    private Activity activity;
    private TimePickerView timePicker;
    private View view;
    private int beforeYear=100;
    private int afterYear=10;

    public TimePickerBottomDialog(Activity activity) {
        super(activity, R.style.transparentFrameWindowStyle);
        this.activity = activity;
    }

    public void setYearTime(int beforeYear,int afterYear){
        this.beforeYear=beforeYear;
        this.afterYear=afterYear;

    }

    private FrameLayout fl_timepicker_container;
    public void showDialog( ) {

        if(view==null){
            view = getLayoutInflater().inflate(R.layout.timepicker_simple,
                    null);
             fl_timepicker_container = (FrameLayout) view.findViewById(R.id.timepicker_container);
        }

        initTimePicker(fl_timepicker_container,activity);

        /*设置默认选中*/
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = getWindow();
        //设置显示动画
        window.setWindowAnimations(R.style.TRM_ANIM_STYLE);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        //以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        /*增加半屏幕判断，如果大于半屏幕，展示半屏幕否则展示正常大小*/
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;//原

        //设置显示位置
        onWindowAttributesChanged(wl);
        //设置点击外围解散
        setCanceledOnTouchOutside(true);
        show();

    }
    private boolean isReset=false;
    public void setReset(boolean  isReset){
        this.isReset=isReset;

    }


    private void initTimePicker(FrameLayout fl, Context context) {
        if(timePicker!=null){
            if(isReset){
                timePicker.setDate( Calendar.getInstance());
                isReset=false;
            }
            timePicker.show();
            return;
        }
        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();

        Calendar endDate = Calendar.getInstance();//结束日期设置

        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH) + 1;
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);
        if ((year - beforeYear) > 0) {
            startDate.set(year - beforeYear, month, day);//开始日期
        }
        endDate.set(year+afterYear,month,day);
        //时间选择器 ，自定义布局
        //选中事件回调
        //是否只显示中间选中项的label文字，false则每项item全部都带有label。
        timePicker = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (chooseListener != null) {
                    chooseListener.chooseData(getTime(date));
                }
            }
        })
        .setDate(selectedDate)
        .setRangDate(startDate, endDate)
        .setDecorView(fl)
        .setLayoutRes(R.layout.fragment_pickerview_time, new CustomListener() {
            @Override
            public void customLayout(View v) {
                final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_end);
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timePicker.returnData();
                       TimePickerBottomDialog.this.dismiss();
                    }
                });
            }
        })
        .setType(new boolean[]{true, true, true, false, false, false})
        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
        .setDividerColor(Color.parseColor("#eeeeee"))
        .setContentSize(15)
        .gravity(Gravity.CENTER)
        .setLineSpacingMultiplier(2.25f)
        .setTextColorCenter(Color.parseColor("#ff5722"))
        .build();
        timePicker.setKeyBackCancelable(false);
        timePicker.show();
    }


    public void setChooseListener(ChooseListener chooseListener){
            this.chooseListener=chooseListener;

    }

//    设置监听
    private ChooseListener chooseListener;
    public interface ChooseListener {

        void chooseData(String startTime);

    };


    /*设置时间格式*/
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        return format.format(date);
    }
}
