package com.manubla.taskmanager.view.home.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manubla.taskmanager.R
import com.manubla.taskmanager.controller.AuthController
import com.manubla.taskmanager.controller.CategoryController
import com.manubla.taskmanager.controller.TodoController
import com.manubla.taskmanager.controller.UserController
import com.manubla.taskmanager.service.response.UserResponse
import com.manubla.taskmanager.util.showLongErrorMessage
import com.manubla.taskmanager.view.auth.AuthActivity
import com.manubla.taskmanager.view.home.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.format.DateTimeFormatter
import kotlin.coroutines.CoroutineContext


class ProfileFragment : BaseFragment() , CoroutineScope {
    private val authController = AuthController()
    private val userController = UserController()
    private val todoController = TodoController()
    private val categoryController = CategoryController()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ) = inflater.inflate(R.layout.fragment_profile, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logoutButton.setOnClickListener { logout() }
        fetchData()
    }



//    private fun loadCharts(categories: List<CategoryResponse>, todosList: List<TodoResponse>) {
//        context?.let { context ->
//            var currentLayout = LayoutInflater.from(context).inflate(
//                R.layout.layout_chart,
//                null, false
//            ) as LinearLayout
//            var currentChart = 0
//            var firstTime = true
//            for (category in categories) {
//                val todosOfCategory = todosList.count { it.category_id == category.id }
//
//                if (todosOfCategory == 0)
//                    continue
//
//                if (firstTime) {
//                    firstTime = false
//                    contentLayout.addView(currentLayout)
//                }
//                else if (currentChart == 2) {
//                    currentChart = 0
//                    currentLayout = LayoutInflater.from(context).inflate(
//                        R.layout.layout_chart,
//                        null, false
//                    ) as LinearLayout
//                    contentLayout.addView(currentLayout)
//                }
//
//                val pieChartView: PieChartView = if (currentChart == 0)
//                    currentLayout.chart1
//                else
//                    currentLayout.chart2.apply { visible() }
//
//                val percentage = ((todosOfCategory.toFloat() / todosList.size.toFloat()) * 100).toString() + "%"
//                val pieData = arrayListOf<SliceValue>()
//                pieData.add(SliceValue(todosOfCategory.toFloat(),
//                    ContextCompat.getColor(context, R.color.colorChartOn)).setLabel(percentage))
//                pieData.add(SliceValue((todosList.size - todosOfCategory).toFloat(),
//                    ContextCompat.getColor(context, R.color.colorChartOff)).setLabel(""))
//
//                val pieChartData = PieChartData(pieData)
//
//                pieChartData.setHasCenterCircle(true)
//                    .setCenterText1(category.name)
//                    .setCenterText1FontSize(20)
//                    .setHasLabels(true)
//                    .centerText1Color = ContextCompat.getColor(context, R.color.colorTextPrimary)
//
//                pieChartView.pieChartData = pieChartData
//                currentChart++
//            }
//        }
//    }


    private fun fetchData() {
        launch(Dispatchers.IO) {
            try {
                val userData: UserResponse = userController.getUserInfo()
                val categories = categoryController.getCategories()
                val todos = todoController.getTodos()
                withContext(Dispatchers.Main) {
                    nameTxt.text = userData.name
                    joinedTxt.text = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm").format(userData.created_at)
                    totalTasksTxt.text = todos.size.toString()
                    tasksCompletedTxt.text = todos.count { it.completed }.toString()

                    val categoriesUsed = hashMapOf<Int, Int?>()
                    for (category in categories) {
                        for (todo in todos) {
                            if (todo.category_id == category.id) {
                                if (!categoriesUsed.contains(category.id))
                                    categoriesUsed[category.id] = 1
                                else
                                    categoriesUsed[category.id] =
                                        categoriesUsed[category.id]?.plus(1)
                            }
                        }
                    }
//                    mostUsedCategoryTxt.text = categoriesUsed.maxBy { it.value ?: 0 }.toString()
                    categoriesUsedTxt.text = categoriesUsed.count().toString()

                }
            } catch (exception: Exception) {
                withContext(Dispatchers.Main) {
                    showLongErrorMessage(getString(R.string.service_error), view, activity)
                }
            }
        }
    }


    private fun logout() {
        launch(Dispatchers.IO) {
            try {
                authController.logout()
                withContext(Dispatchers.Main) {
                    activity?.let {
                        it.startActivity(Intent(it, AuthActivity::class.java))
                        it.finish()
                    }
                }
            } catch (error: Exception) {

            }
        }
    }

    companion object {
        val instance: ProfileFragment = ProfileFragment()
    }
}
