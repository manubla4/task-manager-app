package com.example.listas.views.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listas.R
import com.example.listas.data.Action
import kotlinx.android.synthetic.main.layout_todo_row.view.*
import kotlinx.android.synthetic.main.layout_todo_row_secondary.view.*

class TodoListAdapter(private var actions: List<Action>) :
    RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {

    var data: List<Action>
        get() = actions
        set(value) {
            actions = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = data[position].actionType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return when (viewType) {
            1 -> {
                TodoSecondaryViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_todo_row_secondary, parent, false)
                )
            }
            else -> {
                TodoPrimaryViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_todo_row, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val action = data[position]

        when (holder) {
            is TodoPrimaryViewHolder -> {
                holder.actionText.text = action.description
            }
            is TodoSecondaryViewHolder -> {
                holder.actionTypeText.text = "${action.actionType}"
                holder.actionText.text = action.description
            }
        }
    }

    inner class TodoPrimaryViewHolder(view: View) : TodoViewHolder(view) {
        val actionText: TextView = view.todoAction
    }

    inner class TodoSecondaryViewHolder(view: View) : TodoViewHolder(view) {
        val actionTypeText: TextView = view.todoType
        val actionText: TextView = view.todoActionSecondary
    }

    abstract inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view)

}