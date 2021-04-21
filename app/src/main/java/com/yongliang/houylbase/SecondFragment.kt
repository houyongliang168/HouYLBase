package com.yongliang.houylbase

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    lateinit var mcontext: Context;
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mcontext=context;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            goneView();

        }
    }
    lateinit var convertView:View;
//    lateinit var fm: FragmentManager;
    //    public
    fun addView(vgroup: ViewGroup, fmg: FragmentManager) {
        convertView = LayoutInflater.from(vgroup.context).inflate(
            R.layout.fragment_native_contain1, null
        )
//        this.fm=fm;
        val mFragment: Fragment = this@SecondFragment
        //       管理自身的fragment
        var fm: FragmentManager? = fragmentManager
        if(fm==null){
           fm=fmg;
        }

        //        FragmentManager fm = getChildFragmentManager ();
        val ftt: FragmentTransaction? = fm?.beginTransaction()
        ftt?.replace(R.id.fl_frag_contain1, mFragment)
        ftt?.commitAllowingStateLoss()
        vgroup.addView(convertView)
    }
    fun goneView() {
//        convertView ?.setVisibility(View.GONE)
        val ftt: FragmentTransaction? = fragmentManager?.beginTransaction()
        ftt?.remove( this@SecondFragment)
        ftt?.commitAllowingStateLoss()
    }


//    //    添加view 展示数据
    fun remove(container: ViewGroup?) {
        if (container == null || convertView == null) {
            return
        }
        container.removeView(convertView)
    }


}