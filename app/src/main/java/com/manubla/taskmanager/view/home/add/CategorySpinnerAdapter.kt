package com.manubla.taskmanager.view.home.add

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.manubla.taskmanager.service.response.CategoryResponse

class CategorySpinnerAdapter(ctx: Context, categories: List<CategoryResponse>) :
    ArrayAdapter<CategoryResponse>(ctx, android.R.layout.simple_spinner_dropdown_item, categories) {

    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }
    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }
    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val category = getItem(position)
        val view = recycledView ?: LayoutInflater.from(context).inflate(
            android.R.layout.simple_spinner_dropdown_item,
            parent,
            false
        )
        (view.findViewById<TextView>(android.R.id.text1)).text = category.name
        return view
    }
}