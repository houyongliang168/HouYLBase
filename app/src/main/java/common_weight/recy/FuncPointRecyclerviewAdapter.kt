package com.txx.app.main.section.sectionProducts.funcSectionProduct

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.yongliang.houylbase.R
import com.yongliang.houylbase.databinding.ItemFunctionMoreFooterBinding
import com.yongliang.houylbase.databinding.SectionItemFuncPointNormalBinding
import common_weight.recy.PointBean

class FuncPointRecyclerviewAdapter(var context: Fragment, var list: ObservableList<PointBean>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            val binding: SectionItemFuncPointNormalBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.section_item_func_point_normal, parent, false)
            NormalViewHolder(binding)
        } else {
            val binding: ItemFunctionMoreFooterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_function_more_footer, parent, false)
            FooterViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list!![holder.adapterPosition] ?: return
        if (holder is NormalViewHolder) {
            holder.binding.sbean = item
        }
    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }

    //尾部
    inner class FooterViewHolder(var binding: ItemFunctionMoreFooterBinding) : RecyclerView.ViewHolder(binding.root)

    //正常
    inner class NormalViewHolder(var binding: SectionItemFuncPointNormalBinding) : RecyclerView.ViewHolder(binding.root)

//    //点击事件处理
//    var listener: OnClickListener? = null
//
//    interface OnClickListener {
//        //type 是那种类型，0-7 ，title Type 为跳转到其余四个里面的类型
//        fun  constructor(v: View?, possion: Int, bean: FunctionBean?)
//
//        fun OnLongClickListener(v: View?, possion: Int): Boolean
//    }
}