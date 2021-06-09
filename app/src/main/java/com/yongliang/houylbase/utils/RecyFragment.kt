package com.yongliang.houylbase.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.databinding.observable.DynamicChangeCallback
import com.txx.app.main.section.factory.SectionFactory
import com.yongliang.houylbase.R
import com.yongliang.houylbase.databinding.RecyFragmentBinding
import common_weight.recy.demo1.FuncSectionProduct
import kotlinx.android.synthetic.main.recy_fragment.*
import kotlin.random.Random

class RecyFragment : Fragment() {
    var binding: RecyFragmentBinding? = null
    var list: ObservableList<StringBean> = ObservableArrayList()
    private val mTitlesArrays = arrayOf("新闻", "娱乐", "头条", "八卦")
    var index = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.recy_fragment, container, false)
        binding = DataBindingUtil.bind(view)
        return view
    }
    lateinit var  p1:FuncSectionProduct;
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        p1 = SectionFactory.createProduct(FuncSectionProduct::class.java, this, Bundle())
        p1?.addView(binding?.flContain)

        val manager = LinearLayoutManager(view.context);
        manager.orientation = LinearLayoutManager.VERTICAL
        binding?.recyclerView?.layoutManager = manager

        // 使用原生的 Adapter 即可
        val pointAdapter = StringAdapter(view.context, list)
        list?.addOnListChangedCallback(DynamicChangeCallback(pointAdapter))
        binding?.recyclerView?.adapter = pointAdapter

        binding?.btn?.setOnClickListener {
            addData();
        }
        binding?.btnChange?.setOnClickListener {
            changeData2()
        }
        binding?.btn0?.setOnClickListener {
            p1?.onReflesh();
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        fun newInstance(): RecyFragment {
            return RecyFragment()
        }
    }

    fun addData() {
        val num = Random.nextInt(4)
        list.add(StringBean(mTitlesArrays.get(num)))
//        list.add(mTitlesArrays.get(num))
    }

    fun changeData() {
        val num = Random.nextInt(4)
        list.set(0, StringBean("akjdfkajhd" + index+"----"))
//        list.set(0, "akjdfkajhd" + index+"----")
        index++;
    }
    fun changeData2() {
//        需要变更引用地址
        val num = Random.nextInt(list.size)
        if(list.size>0){
            list[num] .aa= "akjdfkajhd" + index+"----"
            list.set(num,  list[num]);
//            list[0] = "akjdfkajhd" + index+"----"
        }

        index++;
    }
}