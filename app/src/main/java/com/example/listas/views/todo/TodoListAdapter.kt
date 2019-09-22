package com.example.listas.views.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listas.R
import com.example.listas.data.Action
import kotlinx.android.synthetic.main.layout_todo_row.view.*

class TodoListAdapter(private var actions: List<Action>) :
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
        holder.actionText.text = action.description
    }

    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val actionText: TextView = view.todoAction
    }


}