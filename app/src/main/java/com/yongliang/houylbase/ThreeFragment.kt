package com.yongliang.houylbase

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ThreeFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_three, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    //    public
    fun addView(vgroup: ViewGroup,fm: FragmentManager) {
        val convertView = LayoutInflater.from(vgroup.context).inflate(
            R.layout.fragment_native_contain, null
        )
        val mFragment: Fragment = this@ThreeFragment
        //       管理自身的fragment
//        val fm: FragmentManager? = fragmentManager
        //        FragmentManager fm = getChildFragmentManager ();
        val ftt: FragmentTransaction? = fm.beginTransaction()
        ftt?.replace(R.id.fl_frag_contain, mFragment)
        ftt?.commitAllowingStateLoss()
        vgroup.addView(convertView)
    }
}