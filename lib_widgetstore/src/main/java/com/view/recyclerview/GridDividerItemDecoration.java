package com.view.recyclerview;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created by houyongliang on 2018/5/25 15:09.
 * Function(功能):
 * 第一層展示 數據多些
 */

public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpaceLeft = 0;
    private int mSpaceTop = 0;
    private int mSpaceRight = 0;
    private int mSpaceBottom = 0;
    private int mHead = 0;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);


        int spanCount = getSpanCount(parent);
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

        boolean isFirstRaw = isFirstRaw(parent, itemPosition, spanCount);
        if (isFirstRaw) {
            outRect.top = mHead;
        } else {
            outRect.top = mSpaceTop;
        }
        outRect.left = mSpaceLeft;
        outRect.right = mSpaceRight;
        outRect.bottom = mSpaceBottom;
    }

    public GridDividerItemDecoration(int head, int mSpaceLeft, int mSpaceTop, int mSpaceRight, int mSpaceBottom) {
        this.mHead = head;
        this.mSpaceLeft = mSpaceLeft;
        this.mSpaceTop = mSpaceTop;
        this.mSpaceRight = mSpaceRight;
        this.mSpaceBottom = mSpaceBottom;
    }

    private boolean isFirstColumn(RecyclerView parent, int pos, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = (layoutManager instanceof GridLayoutManager) ? ((GridLayoutManager) layoutManager).getOrientation() : ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == GridLayoutManager.VERTICAL) {
                if (pos % spanCount == 0) {
                    return true;
                }
            } else {
                if (pos < spanCount) {
                    return true;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                //每一个都是第一列，也是最后一列
                return true;
            } else {
                if (pos == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = (layoutManager instanceof GridLayoutManager) ? ((GridLayoutManager) layoutManager).getOrientation() : ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == GridLayoutManager.VERTICAL) {
                //最后一列或者不能整除的情况下最后一个
                if ((pos + 1) % spanCount == 0 /**|| pos==childCount-1*/) {// 如果是最后一列
                    return true;
                }
            } else {
                if (pos >= childCount - spanCount && childCount % spanCount == 0) {
                    //整除的情况判断最后一整列
                    return true;
                } else if (childCount % spanCount != 0 && pos >= spanCount * (childCount / spanCount)) {
                    //不能整除的情况只判断最后几个
                    return true;
                }
//                if(pos>=childCount-spanCount){
//                    return true;
//                }
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                //每一个都是第一列，也是最后一列
                return true;
            } else {
                if (pos == childCount - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = (layoutManager instanceof GridLayoutManager) ? ((GridLayoutManager) layoutManager).getOrientation() : ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == GridLayoutManager.VERTICAL) {
                if (pos >= childCount - spanCount && childCount % spanCount == 0) {
                    //整除的情况判断最后一整行
                    return true;
                } else if (childCount % spanCount != 0 && pos >= spanCount * (childCount / spanCount)) {
                    //不能整除的情况只判断最后几个
                    return true;
                }
//                if(pos>=childCount-spanCount){
//                    return true;
//                }
            } else {
                //最后一行或者不能整除的情况下最后一个
                if ((pos + 1) % spanCount == 0 /**|| pos==childCount-1*/) {// 如果是最后一行
                    return true;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                if (pos == childCount - 1) {
                    return true;
                }
            } else {
                //每一个都是第一行，也是最后一行
                return true;
            }
        }
        return false;
    }


    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }


    private boolean isFirstRaw(RecyclerView parent, int pos, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = (layoutManager instanceof GridLayoutManager) ? ((GridLayoutManager) layoutManager).getOrientation() : ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == GridLayoutManager.VERTICAL) {
                if (pos < spanCount) {
                    return true;
                }
            } else {
                if (pos % spanCount == 0) {
                    return true;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                if (pos == 0) {
                    return true;
                }
            } else {
                //每一个都是第一行，也是最后一行
                return true;
            }
        }
        return false;
    }


}
