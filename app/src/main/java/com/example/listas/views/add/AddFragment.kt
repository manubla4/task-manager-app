package com.example.listas.views.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.listas.R
import com.example.listas.extensions.textString
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(input: String)
    }

    private var listener: OnFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addButton.setOnClickListener {
            onAddButtonPressed()
        }
    }

    private fun onAddButtonPressed() {
        if (input.textString().isEmpty()) {
            return
        }
        listener?.onFragmentInteraction(input.textString())
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
