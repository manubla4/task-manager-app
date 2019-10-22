package com.manubla.taskmanager.view.home.todo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.manubla.taskmanager.R
import com.manubla.taskmanager.controller.TodoController
import com.manubla.taskmanager.data.Action
import com.manubla.taskmanager.data.TodoAdapterItem
import com.manubla.taskmanager.extension.gone
import com.manubla.taskmanager.extension.visible
import com.manubla.taskmanager.service.response.TodoResponse
import com.manubla.taskmanager.util.showLongErrorMessage
import com.manubla.taskmanager.util.showLongMessage
import com.manubla.taskmanager.view.home.BaseFragment
import com.manubla.taskmanager.view.home.HomeActivity
import com.manubla.taskmanager.view.home.add.AddActivity
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import kotlin.coroutines.CoroutineContext

class TodoFragment : BaseFragment(), CoroutineScope, TodoListAdapter.OnAdapterInteraction {
    private val todoController = TodoController()
    private var todosList: List<TodoResponse> = listOf()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_todo, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            todoList.layoutManager = LinearLayoutManager(it)
            todoList.adapter = TodoListAdapter(this, arrayListOf())
            todoList.setHasFixedSize(true)
        }
        addFab.setOnClickListener {
            activity?.startActivityForResult(
                Intent(activity, AddActivity::class.java),
                HomeActivity.ADD_TODO_REQUEST_CODE
            )
        }
        swipeLayout.setOnRefreshListener {
            fetchTodos()
        }
        startLoading()
        fetchTodos()
    }

    private fun startLoading(){
        todoList.gone()
        txtEmpty.gone()
        progress.visible()
    }

    private fun stopLoading(){
        progress.gone()
        todoList.visible()
        listener?.onFragmentInteraction(Action(HomeActivity.HIDE_PROGRESS_ACTION))
        if (swipeLayout.isRefreshing) {
            showLongMessage(getString(R.string.refresh_successful), view, activity)
            swipeLayout.isRefreshing = false
        }
    }

    private fun fetchTodos() {
        launch(Dispatchers.IO) {
            try {
                todosList = todoController.getTodos()
                withContext(Dispatchers.Main) {
                    if (todosList.isNotEmpty()) {
                        val sortedResponse = todosList.sortedBy { it.due_date }
                        val adapterList = arrayListOf<TodoAdapterItem>()
                        var currentDate = sortedResponse[0].due_date
                        for (obj in sortedResponse) {

                            if (sortedResponse.indexOf(obj) == 0 ||
                                    (currentDate.truncatedTo(ChronoUnit.DAYS) !=
                                    obj.due_date.truncatedTo(ChronoUnit.DAYS))) {
                                currentDate = obj.due_date
                                val item = TodoAdapterItem(
                                    TodoListAdapter.viewTypeDate,
                                    0,
                                    "",
                                    "",
                                    "",
                                    false,
                                    obj.due_date.dayOfWeek.toString().toLowerCase().capitalize(),
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy").format(obj.due_date)
                                )
                                adapterList.add(item)
                            }

                            val item = TodoAdapterItem(
                                TodoListAdapter.viewTypeTodo,
                                obj.id,
                                obj.description,
                                obj.priority,
                                obj.category.color,
                                obj.completed,
                                "",
                                ""
                            )
                            adapterList.add(item)
                        }

                        todoList.adapter.let {
                            (it as? TodoListAdapter)?.let { todoListAdapter ->
                                todoListAdapter.data = adapterList
                            }
                        }

                    }
                    else {
                        todoList.gone()
                        txtEmpty.visible()
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


    fun createTodo(todo: TodoResponse) {
        launch(Dispatchers.IO) {
            try {
                todoController.createTodo(todo.priority, todo.description, todo.due_date, todo.category_id)
                withContext(Dispatchers.Main) {
                    fetchTodos()
                }
            } catch (exception: Exception) {
                withContext(Dispatchers.Main) {
                    stopLoading()
                    showLongErrorMessage(getString(R.string.service_error), view, activity)
                }
            }
        }
    }

    private fun removeTodo(todo: TodoResponse) {
        launch(Dispatchers.IO) {
            try {
                todoController.removeTodo(todo.id)
                withContext(Dispatchers.Main) {
                    fetchTodos()
                }
            } catch (exception: Exception) {}
            finally {
                withContext(Dispatchers.Main) {
                    fetchTodos()
                }
            }
        }
    }

    private fun modifyTodo(completed: Boolean, todo: TodoResponse) {
        launch(Dispatchers.IO) {
            try {
                todoController.modifyTodo(
                    todo.id,
                    todo.priority,
                    todo.description,
                    todo.due_date,
                    completed,
                    todo.category_id)
                withContext(Dispatchers.Main) {
                    fetchTodos()
                }
            } catch (exception: Exception) {}
            finally {
                withContext(Dispatchers.Main) {
                    fetchTodos()
                }
            }
        }
    }


    override fun onModifyTodo(completed: Boolean, idTodo: Int) {
        for (todo in todosList) {
            if (todo.id == idTodo) {
                modifyTodo(completed, todo)
                break
            }
        }
    }

    override fun onRemoveTodo(idTodo: Int) {
        listener?.onFragmentInteraction(Action(HomeActivity.SHOW_PROGRESS_ACTION))
        for (todo in todosList) {
            if (todo.id == idTodo) {
                removeTodo(todo)
                break
            }
        }
    }

    fun onAddTodo(todo: TodoResponse) {
        startLoading()
        createTodo(todo)
    }

    companion object {
        val instance: TodoFragment = TodoFragment()
    }
}
