package com.manubla.taskmanager.view.home.todo

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.manubla.taskmanager.R
import com.manubla.taskmanager.data.Priority
import com.manubla.taskmanager.data.TodoAdapterItem
import com.manubla.taskmanager.service.response.TodoResponse
import kotlinx.android.synthetic.main.layout_date_row.view.*
import kotlinx.android.synthetic.main.layout_todo_row.view.*


class TodoListAdapter(private val listener: OnAdapterInteraction?, private var items: List<TodoAdapterItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<TodoAdapterItem>
        get() = items
        set(value) {
            items = value
            notifyDataSetChanged()
        }

    fun addItem(item: TodoAdapterItem) {
        (data as ArrayList).add(item)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == viewTypeTodo) {
            TodoViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_todo_row, parent, false)
            )
        } else {
            DateViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_date_row, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = data[position]
        return item.viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]

        if (item.viewType == viewTypeTodo) {
            val color = Color.parseColor(item.categoryColor)
            (holder as TodoViewHolder).apply {
                descriptionText.text = item.description
                priorityText.text = Priority.valueOf(item.priority).description
                viewCategory.setBackgroundColor(color)
                if (item.completed) {
                    checkboxState.isChecked = true
                    descriptionText.paintFlags =
                        descriptionText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    priorityText.paintFlags =
                        priorityText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }

                checkboxState.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        descriptionText.paintFlags =
                            descriptionText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        priorityText.paintFlags =
                            priorityText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    } else {
                        descriptionText.paintFlags =
                            descriptionText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                        priorityText.paintFlags =
                            priorityText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    }
                    listener?.onModifyTodo(isChecked, item.idTodo)
                }

                removeButton.setOnClickListener {
                    listener?.onRemoveTodo(item.idTodo)
                }
            }
        }

        else {
            (holder as DateViewHolder).apply {
                dayTxt.text = item.dueDayOfWeek
                dateTxt.text = item.dueDate
            }

        }
    }

    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val viewCategory: View = view.viewCategory
        val descriptionText: TextView = view.txtDescription
        val priorityText: TextView = view.txtPriority
        val checkboxState: CheckBox = view.checkBoxState
        val removeButton: ImageButton = view.removeButton
    }

    inner class DateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayTxt: TextView = view.dayTxt
        val dateTxt: TextView = view.dateTxt
    }

    interface OnAdapterInteraction {
        fun onRemoveTodo(idTodo: Int)
        fun onModifyTodo(completed: Boolean, idTodo: Int)
    }

    companion object {
        val viewTypeTodo = 1
        val viewTypeDate = 2
    }

}