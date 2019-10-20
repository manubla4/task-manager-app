package com.manubla.taskmanager.view.home

import android.content.Context
import androidx.fragment.app.Fragment
import com.manubla.taskmanager.data.Action


open class BaseFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener)
            listener = context
        else
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(input: Action)
    }
}
