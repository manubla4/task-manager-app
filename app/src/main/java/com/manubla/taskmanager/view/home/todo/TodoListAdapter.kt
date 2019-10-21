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
import com.manubla.taskmanager.service.response.TodoResponse
import kotlinx.android.synthetic.main.layout_todo_row.view.*


class TodoListAdapter(private val listener: OnAdapterInteraction?, private var todos: List<TodoResponse>) :
    RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {

    var data: List<TodoResponse>
        get() = todos
        set(value) {
            todos = value
            notifyDataSetChanged()
        }

    fun addItem(todo: TodoResponse) {
        (data as ArrayList).add(todo)
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
        val todo = data[position]
        val color = Color.parseColor(todo.category.color)
        holder.descriptionText.text = todo.description
        holder.priorityText.text = Priority.valueOf(todo.priority).description
        holder.viewCategory.setBackgroundColor(color)
        if (todo.completed) {
            holder.checkboxState.isChecked = true
            holder.descriptionText.paintFlags =
                holder.descriptionText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.priorityText.paintFlags =
                holder.priorityText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        holder.checkboxState.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                holder.descriptionText.paintFlags =
                    holder.descriptionText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.priorityText.paintFlags =
                    holder.priorityText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.descriptionText.paintFlags =
                    holder.descriptionText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                holder.priorityText.paintFlags =
                    holder.priorityText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            listener?.onModifyTodo(isChecked, todo)
        }

        holder.removeButton.setOnClickListener {
            listener?.onRemoveTodo(todo)
        }
    }

    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val viewCategory: View = view.viewCategory
        val descriptionText: TextView = view.txtDescription
        val priorityText: TextView = view.txtPriority
        val checkboxState: CheckBox = view.checkBoxState
        val removeButton: ImageButton = view.removeButton
    }

    interface OnAdapterInteraction{
        fun onRemoveTodo(todo: TodoResponse)
        fun onModifyTodo(state: Boolean, todo: TodoResponse)
    }

}