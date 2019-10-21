package com.manubla.taskmanager.view.home.add

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import com.manubla.taskmanager.R
import com.manubla.taskmanager.controller.CategoryController
import com.manubla.taskmanager.data.Priority
import com.manubla.taskmanager.extension.gone
import com.manubla.taskmanager.extension.textString
import com.manubla.taskmanager.extension.visible
import com.manubla.taskmanager.service.response.CategoryResponse
import com.manubla.taskmanager.service.response.TodoResponse
import com.manubla.taskmanager.util.showLongErrorMessage
import com.manubla.taskmanager.view.home.BaseFragment
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import kotlin.coroutines.CoroutineContext


class AddFragment : BaseFragment() , CoroutineScope {

    private val prioritiesArray = arrayListOf(Priority.LOW.description,
        Priority.MEDIUM.description, Priority.HIGH.description)
    private val dateFormat = "dd-MM-yyyy"
    private val categoryController = CategoryController()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

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

        lateinit var prioritiesArrayAdapter: ArrayAdapter<String>
        context?.let {
            prioritiesArrayAdapter = ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                prioritiesArray
            )
        }
        prioritiesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        prioritySpinner.adapter = prioritiesArrayAdapter

        val dateDialogListener =
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val zonedDateTime = ZonedDateTime.of(year, month, day,
                    0, 0, 0, 0, ZoneId.systemDefault())
                dueDateInput.setText(DateTimeFormatter.ofPattern(dateFormat).format(zonedDateTime))
                dueDateInput.contentDescription = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(zonedDateTime)
                contentLayout.requestFocus()
            }

        dueDateInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                context?.let {
                    val calendar = GregorianCalendar()
                    val dialog = DatePickerDialog(
                        it, dateDialogListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DATE)
                    )
                    dialog.setOnDismissListener { contentLayout.requestFocus() }
                    dialog.show()
                }
            }
        }

        startLoading()
        fetchCategories()
    }

    private fun startLoading() {
        contentLayout.gone()
        progress.visible()
    }

    private fun stopLoading() {
        progress.gone()
        contentLayout.visible()
    }

    private fun onAddButtonPressed() {

        descriptionInputLayout.error = null
        dueDateInputLayout.error = null
        var valid = true
        if (descriptionInput.textString().isEmpty()) {
            descriptionInputLayout.error = getString(R.string.field_empty)
            valid = false
        }
        if (dueDateInput.textString().isEmpty()) {
            dueDateInputLayout.error = getString(R.string.field_empty)
            valid = false
        }
        else if (!ZonedDateTime.parse(dueDateInput.contentDescription, DateTimeFormatter.ISO_ZONED_DATE_TIME)
                .isAfter(ZonedDateTime.now())) {
            dueDateInputLayout.error = getString(R.string.add_fragment_due_date_later)
            valid = false
        }

        if (valid) {
            val category = categorySpinner.selectedItem as CategoryResponse

            val todo = TodoResponse(
                -1,
                descriptionInput.textString(),
                false,
                ZonedDateTime.parse(dueDateInput.contentDescription, DateTimeFormatter.ISO_ZONED_DATE_TIME),
                getPriority(prioritySpinner.selectedItem as String),
                category.id,
                category
            )

            listener?.onFragmentInteraction(todo)
        }
    }

    private fun getPriority(value: String): String {
        return when (value) {
            Priority.HIGH.description -> Priority.HIGH.code
            Priority.MEDIUM.description -> Priority.MEDIUM.code
            else -> Priority.LOW.code
        }
    }


    private fun fetchCategories() {
        launch(Dispatchers.IO) {
            try {
                val response: List<CategoryResponse> = categoryController.getCategories()

                withContext(Dispatchers.Main) {
                    context?.let {
                        val spinnerArrayAdapter = CategorySpinnerAdapter(it, response)
                        categorySpinner.adapter = spinnerArrayAdapter
                    }
                    stopLoading()
                }
            } catch (exception: Exception) {
                withContext(Dispatchers.Main) {
                    stopLoading()
                    showLongErrorMessage(getString(R.string.service_error), view, activity)
                }
            }
        }
    }

}
