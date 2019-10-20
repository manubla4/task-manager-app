package com.manubla.taskmanager.view.home.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.manubla.taskmanager.R
import com.manubla.taskmanager.controller.TodoController
import com.manubla.taskmanager.data.Action
import com.manubla.taskmanager.extension.gone
import com.manubla.taskmanager.extension.visible
import com.manubla.taskmanager.service.response.TodosReponse
import com.manubla.taskmanager.util.showLongErrorMessage
import com.manubla.taskmanager.view.home.BaseFragment
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TodoFragment : BaseFragment() , CoroutineScope {

    private val todoController = TodoController()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    var todos: List<Action> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todos = arguments?.getParcelableArrayList(actionParams) ?: listOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_todo, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            todoList.layoutManager = LinearLayoutManager(it)
            todoList.adapter = TodoListAdapter(todos, it)
        }
    }

    override fun onResume() {
        super.onResume()
        retrieveTodos()
    }

    private fun startLoading(){
        todoList.gone()
        progress.visible()
    }

    private fun stopLoading(){
        progress.gone()
        todoList.visible()
    }

    private fun retrieveTodos(){
        startLoading()
        launch(Dispatchers.IO) {
            try {
                val response = todoController.getTodos()
                withContext(Dispatchers.Main) {
                    todoList.adapter.let {
                        (it as? TodoListAdapter)?.let { todoListAdapter ->
                            todoListAdapter.data = response.todos
                        }
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


//    fun updateAdapterData(todos: ArrayList<Action>) {
//        todoList.visibility = View.VISIBLE
//        txtEmpty.visibility = View.GONE
//        todoList.adapter.let {
//            (it as? TodoListAdapter)?.let { todoListAdapter ->
//                todoListAdapter.data = todos
//            }
//        }
//    }

    companion object {
        const val actionParams = "Params:Actions"

        fun newInstance(actions: ArrayList<Action>) = TodoFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(actionParams, actions)
            }
        }
    }
}
