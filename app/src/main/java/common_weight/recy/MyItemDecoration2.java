package common_weight.recy;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by houyongliang on 2018/5/10 11:23.
 * Function(功能):
 *
 */

public class MyItemDecoration2 extends RecyclerView.ItemDecoration {
    private int topSpace;
    private int downSpace;

    public MyItemDecoration2(int topSpace, int downSpace) {
        this.topSpace = topSpace;
        this.downSpace = downSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int left = 0, right = 0, top = 0, bottom = 0;
        int childCount = parent.getAdapter().getItemCount();
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == LinearLayoutManager.VERTICAL) {

            if(itemPosition==0){
                //第一个item，需要绘制就在左边留出偏移
                top=topSpace;
            }else {
                top=0;
            }
//            if (itemPosition == childCount - 1) {
//                //最后一个item，需要绘制则留出偏移，默认留出，不需要则设置right为0
//                right = 0;
//            }
            bottom=downSpace;
        } else {


        }
        outRect.set(left, top, right, bottom);

    }


}
