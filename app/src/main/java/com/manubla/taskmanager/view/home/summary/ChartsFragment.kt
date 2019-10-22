package com.manubla.taskmanager.view.home.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.manubla.taskmanager.R
import com.manubla.taskmanager.controller.CategoryController
import com.manubla.taskmanager.controller.TodoController
import com.manubla.taskmanager.extension.gone
import com.manubla.taskmanager.extension.visible
import com.manubla.taskmanager.service.response.CategoryResponse
import com.manubla.taskmanager.service.response.TodoResponse
import com.manubla.taskmanager.util.showLongErrorMessage
import com.manubla.taskmanager.view.home.BaseFragment
import kotlinx.android.synthetic.main.fragment_charts.*
import kotlinx.android.synthetic.main.layout_chart.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.view.PieChartView
import kotlin.coroutines.CoroutineContext


class ChartsFragment: BaseFragment() , CoroutineScope {
    private val todoController = TodoController()
    private val categoryController = CategoryController()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_charts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startLoading()
    }

    override fun onResume() {
        super.onResume()
        contentLayout.removeAllViews()
        fetchData()
    }

    private fun startLoading(){
        scrollView.gone()
        progress.visible()
    }

    private fun stopLoading(){
        progress.gone()
        scrollView.visible()
    }

    private fun fetchData() {
        launch(Dispatchers.IO) {
            try {
                val categories = categoryController.getCategories()
                val todosList = todoController.getTodos()
                withContext(Dispatchers.Main) {
                    loadCharts(categories, todosList)
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

    private fun loadCharts(categories: List<CategoryResponse>, todosList: List<TodoResponse>) {
        context?.let { context ->
            var currentLayout = LayoutInflater.from(context).inflate(
                R.layout.layout_chart,
                null, false
            ) as LinearLayout
            var currentChart = 0
            var firstTime = true
            for (category in categories) {
                val todosOfCategory = todosList.count { it.category_id == category.id }

                if (todosOfCategory == 0)
                    continue

                if (firstTime) {
                    firstTime = false
                    contentLayout.addView(currentLayout)
                }
                else if (currentChart == 2) {
                    currentChart = 0
                    currentLayout = LayoutInflater.from(context).inflate(
                        R.layout.layout_chart,
                        null, false
                    ) as LinearLayout
                    contentLayout.addView(currentLayout)
                }

                val pieChartView: PieChartView = if (currentChart == 0)
                    currentLayout.chart1
                else
                    currentLayout.chart2.apply { visible() }

                val percentage = ((todosOfCategory.toFloat() / todosList.size.toFloat()) * 100).toString() + "%"
                val pieData = arrayListOf<SliceValue>()
                pieData.add(SliceValue(todosOfCategory.toFloat(),
                    ContextCompat.getColor(context, R.color.colorChartOn)).setLabel(percentage))
                pieData.add(SliceValue((todosList.size - todosOfCategory).toFloat(),
                    ContextCompat.getColor(context, R.color.colorChartOff)).setLabel(""))

                val pieChartData = PieChartData(pieData)

                pieChartData.setHasCenterCircle(true)
                    .setCenterText1(category.name)
                    .setCenterText1FontSize(20)
                    .setHasLabels(true)
                    .centerText1Color = ContextCompat.getColor(context, R.color.colorTextPrimary)

                pieChartView.pieChartData = pieChartData
                currentChart++
            }
        }
    }

    companion object {
        val instance: ChartsFragment = ChartsFragment()
    }
}
