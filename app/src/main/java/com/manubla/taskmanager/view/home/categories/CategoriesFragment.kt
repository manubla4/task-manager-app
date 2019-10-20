package com.manubla.taskmanager.view.home.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manubla.taskmanager.R
import com.manubla.taskmanager.view.home.BaseFragment

//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

class CategoriesFragment : BaseFragment() {

//    private var param1: String? = null
//    private var param2: String? = null


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.apply {
//            param1 = getString(ARG_PARAM1)
//            param2 = getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }




    companion object {
        fun newInstance() = CategoriesFragment()

//        fun newInstance(param1: String, param2: String) =
//            SummaryFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }
}
