package com.manubla.taskmanager.views.todo

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.manubla.taskmanager.R
import com.manubla.taskmanager.data.Action
import com.manubla.taskmanager.data.Category
import kotlinx.android.synthetic.main.layout_todo_row.view.*


class TodoListAdapter(private var actions: List<Action>, private var context : Context) :
    RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {

    var data: List<Action>
        get() = actions
        set(value) {
            actions = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_todo_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val action = data[position]
        holder.descriptionText.text = action.description
        holder.priorityText.text = action.priority.description
        when(action.category) {
            Category.WORK -> holder.viewCategory.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWork))
            Category.STUDY -> holder.viewCategory.setBackgroundColor(ContextCompat.getColor(context, R.color.colorStudy))
            Category.SHOPPING -> holder.viewCategory.setBackgroundColor(ContextCompat.getColor(context, R.color.colorShopping))
            Category.LEISURE -> holder.viewCategory.setBackgroundColor(ContextCompat.getColor(context, R.color.colorLeisure))
        }
        holder.checkboxState.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                holder.descriptionText.paintFlags = holder.descriptionText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.priorityText.paintFlags = holder.priorityText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.descriptionText.paintFlags = holder.descriptionText.paintFlags xor Paint.STRIKE_THRU_TEXT_FLAG
                holder.priorityText.paintFlags = holder.priorityText.paintFlags xor Paint.STRIKE_THRU_TEXT_FLAG
            }
        }
    }

    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val viewCategory: View = view.viewCategory
        val descriptionText: TextView = view.txtDescription
        val priorityText: TextView = view.txtPriority
        val checkboxState: CheckBox = view.checkBoxState
    }

}