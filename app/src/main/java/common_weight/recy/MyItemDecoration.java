package common_weight.recy;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by houyongliang on 2018/5/10 11:23.
 * Function(功能):
 *
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private int topSpace;
    private int downSpace;

    public MyItemDecoration(int topSpace,int downSpace) {
        this.topSpace = topSpace;
        this.downSpace = downSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

         outRect.bottom = downSpace;//条目下间距
        outRect.top = topSpace;//条目 上间距

    }


}
