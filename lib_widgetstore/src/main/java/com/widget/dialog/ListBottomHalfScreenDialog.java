package com.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.utils.DensityUtil;
import com.utils.ScreenUtils;
import com.yongliang.widgetstore.R;

import java.util.List;

/**
 * 建立统一的 dialog  从下弹框
 * Created by 41113 on 2018/9/19.
 */

public class ListBottomHalfScreenDialog extends Dialog {


    private Activity activity;

    public ListBottomHalfScreenDialog( Activity activity) {
        super(activity, R.style.transparentFrameWindowStyle);
        this.activity = activity;
    }



    public void showDialog(final List<String> strs, String selectItem, final OnClick onclick) {
        if (strs == null) {
            return;
        }

        View view = getLayoutInflater().inflate(R.layout.basemodule_dialog_list_bottom_halfscreen,
                null);
        ListView lv = (ListView) view.findViewById(R.id.lv_dialog);
        final MyAdpater myAdpater = new MyAdpater(getContext(), strs);
        lv.setAdapter(myAdpater);
        /*设置默认值*/

        if (!TextUtils.isEmpty(selectItem)) {

            for (int i = 0; i < strs.size(); i++) {
                if (selectItem.equals(strs.get(i))) {
                    myAdpater.setIsDef(true);
                    myAdpater.setDefSelect(i);
                    break;
                }
            }
        } else {
            myAdpater.setIsDef(false);
        }

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
        int height1 = strs.size() * (DensityUtil.dip2px(getContext(), 44.5f));//原
        int halfHeight = ScreenUtils.getScreenHeight(getContext()) / 2;
        wl.height = (height1 > halfHeight ? halfHeight : height);

    //设置显示位置
        onWindowAttributesChanged(wl);
    //设置点击外围解散
        setCanceledOnTouchOutside(true);
        show();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (onclick != null) {
                    onclick.onItemClick(strs.get(i),i);
                }

                dismiss();
            }
        });
    }


    /*弹框 adpter list  传入 上下文 和 list<String> 对象*/
    public class MyAdpater extends BaseAdapter {
        private List<String> lists;
        private int defItem;//声明默认选中的项
        private boolean isDef = false;
        private Context context;

        public MyAdpater(Context context, List<String> lists) {
            this.context = context;
            this.lists = lists;
        }


        TextView tv;

        /**
         * 适配器中添加这个方法
         */
        public void setDefSelect(int position) {
            this.defItem = position;
            notifyDataSetChanged();
        }

        /*设置是否默认*/
        public void setIsDef(boolean isDef) {
            this.isDef = isDef;
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int i) {
            return lists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertview, ViewGroup viewGroup) {
            if (convertview == null) {
                convertview = LayoutInflater.from(context).inflate(R.layout.basemodule_item_half_screen, null);
            }
            tv = (TextView) convertview.findViewById(R.id.tv_item);
            tv.setText(lists.get(i));
            Resources resource = (Resources) context.getResources();
            if (isDef && defItem == i) {
                ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.colorff5722);
                if (csl != null) {
                    tv.setTextColor(csl);
                }
            } else {
                ColorStateList cs2 = (ColorStateList) resource.getColorStateList(R.color.color666666);
                if (cs2 != null) {
                    tv.setTextColor(cs2);
                }
            }
            return convertview;
        }
    }





    public interface OnClick {
        void onItemClick(String str, int possion);

    }

}
