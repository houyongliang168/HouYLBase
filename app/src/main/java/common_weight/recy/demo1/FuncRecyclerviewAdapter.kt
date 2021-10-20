package common_weight.recy.demo1;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yongliang.houylbase.R
import com.yongliang.houylbase.databinding.ItemFunctionMoreFooterBinding
import com.yongliang.houylbase.databinding.SectionItemFuncNormalBinding

class FuncRecyclerviewAdapter(var context: Context, var list: ObservableList<ProcessItemBean>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var clickTime: Long = 0
//    多类型处理  固定类型为1
    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            val binding: SectionItemFuncNormalBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.section_item_func_normal, parent, false)
            NormalViewHolder(binding)
        } else {
            val binding: ItemFunctionMoreFooterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_function_more_footer, parent, false)
            FooterViewHolder(binding)
        }
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list?.get(position) ?: return
        if (item == null) {
            return
        }
        if (item.item == null) {
            return
        }
//      点击事件处理
        holder.itemView.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            if (currentTime - clickTime > 200) {
                clickTime = currentTime
                listener?.OnClickListener(holder.itemView, holder.getAdapterPosition(), item)
            }
        }
        if (holder is NormalViewHolder) {
            //默认没有小红点
            holder.binding.ivPointSectionFunc.visibility = View.GONE

            //活动 及图片显示处理
//            Glide.with(context)
//                    .load(item?.item?.imgurl)
//                    .into(holder.binding.ivSectionFunc)
            // 通用内容处理
            holder.binding.tvSectionFunc.text = item.item?.title?.trim() ?: "--"
            // 新字处理 显示不显示新字  0 不是新字  1 是新字
            val isNew = item.item.isNew

            if (isNew == "1") {
                if(item?.displayNew != 1){
//                    如果之前处理过 则不处理 处理后 状态变为1  displayNew 状态只有 0  和 1 两种状态
//                    新字处理
                    item?.displayNew = 0
//                    val newCode = ProcessNew.getNew(item.item?.funcId)
//                    when (newCode) {
//                        0 -> {
//                            item?.displayNew = 0
//                        }
//                        else -> {
//                            item?.displayNew = 1
//                        }
//                    }
                }
//
            } else {
                item?.displayNew = 1
            }
            holder.binding.ivNewSectionFunc.visibility = when (item?.displayNew) {
                0 -> {
                    View.VISIBLE
                }
                else -> {
                    View.GONE
                }
            }
        }
    }

    override fun getItemCount(): Int {
      var ss=0;

        list?.let {
            ss=it.size
        }

        return ss
    }

    //尾部
    inner class FooterViewHolder(var binding: ItemFunctionMoreFooterBinding) : RecyclerView.ViewHolder(binding.root)

    //正常
    inner class NormalViewHolder(var binding: SectionItemFuncNormalBinding) : RecyclerView.ViewHolder(binding.root)

    //点击事件处理
    var listener: OnClickListener? = null

    interface OnClickListener {
        //type 是那种类型，0-7 ，title Type 为跳转到其余四个里面的类型
        fun OnClickListener(v: View?, possion: Int, bean: ProcessItemBean?)

        fun OnLongClickListener(v: View?, possion: Int): Boolean
    }
}