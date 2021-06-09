package com.yongliang.houylbase.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import com.yongliang.widgetstore.R
import java.util.*

/**
 * Created by houyongliang on 2018/5/3 13:23.
 * Function(功能):
 */
class StringAdapter(private val mContext: Context, private val list: ObservableList<StringBean>?) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext)
                .inflate(R.layout.item_dialog_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_label.text = list!![position].aa +position

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //        FrameLayout fl;
    var tv_label: TextView

    init {
        tv_label = itemView.findViewById<View>(R.id.tv_label_name) as TextView
        //            fl = (FrameLayout) itemView.findViewById(R.id.fl);
    }
}
