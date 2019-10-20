package com.manubla.taskmanager.view.home.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.manubla.taskmanager.R
import com.manubla.taskmanager.data.Action
import com.manubla.taskmanager.data.Priority
import com.manubla.taskmanager.extension.textString
import com.manubla.taskmanager.view.home.BaseFragment
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : BaseFragment() {

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

        if (inputDescription.textString().isEmpty()) {
            Toast.makeText(context, "You must insert a description", Toast.LENGTH_LONG).show()
            return
        }
        if (radioGroupCategory.checkedRadioButtonId == -1) {
            Toast.makeText(context, "You must insert a category", Toast.LENGTH_LONG).show()
            return
        }
        if (radioGroupPriority.checkedRadioButtonId == -1) {
            Toast.makeText(context, "You must insert a priority", Toast.LENGTH_LONG).show()
            return
        }

        var action = Action(
            if (radioHighPriority.isChecked) Priority.HIGH
            else if (radioMediumPriority.isChecked) Priority.MEDIUM
            else Priority.LOW,
            if (radioCategoryWork.isChecked) Category.WORK
            else if (radioCategoryStudy.isChecked) Category.STUDY
            else if (radioCategoryShopping.isChecked) Category.SHOPPING
            else Category.LEISURE,
            inputDescription.textString())
        listener?.onFragmentInteraction(action)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
