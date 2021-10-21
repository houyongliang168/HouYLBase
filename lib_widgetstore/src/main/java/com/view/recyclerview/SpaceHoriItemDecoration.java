package com.view.recyclerview;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by REN SHI QIAN on 2017/8/19.
 * RecyclerView的间隔绘制
 */

public class SpaceHoriItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceHoriItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
//        To get the current item/child position:
        final int itemPosition = parent.getChildAdapterPosition (view);
        if (itemPosition == RecyclerView.NO_POSITION) {
            return;
        }
//        To get the number of items/childs:
        final int itemCount = state.getItemCount ();
        /** last position */
        if (itemPosition == 0 && itemCount == 0) {
            outRect.right = space;
        } else if (itemCount > 0 && itemPosition == itemCount - 1) {
            outRect.right = space;
        }


    }
}
