package com.yongliang.houylbase.utils.tabs2

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.view.recyclerview.adpter.StringAdapter
import com.yongliang.houylbase.R
import mvvm.view.recyclerview.LayoutManagers

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: String? = null
   lateinit var recycler_view:RecyclerView;
    val ls=ArrayList<String>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
        ls.add("ahkdjfhkajhdf")
        ls.add("ahkdjfhkajhdf")
        ls.add("ahkdjfhkajhdf")
        ls.add("ahkdjfhkajhdf")
        ls.add("ahkdjfhkajhdf")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        recycler_view=view.findViewById(R.id.recycler_view)
        //      设置视图及数据
        val manager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recycler_view.layoutManager=manager
        val adapter =  StringAdapter (getContext (), ls);

        recycler_view.setAdapter(adapter);

        when(param1){
            (1) -> view.setBackgroundColor(Color.RED)
            (2) -> view.setBackgroundColor(Color.GREEN)
            (3) -> view.setBackgroundColor(Color.BLUE)
            (4) -> view.setBackgroundColor(Color.YELLOW)

        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AFragment.  , param2: String
         */
        @JvmStatic
        fun newInstance(param1: Int) =
            AFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}