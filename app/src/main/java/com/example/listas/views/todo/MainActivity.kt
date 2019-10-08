package com.example.listas.views.todo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.listas.R
import com.example.listas.data.Action
import com.example.listas.views.add.AddActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val addActionRequestCode = 1001

    private val actions : ArrayList<Action> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                TodoFragment.newInstance(actions), TodoFragment.todoFragmentTag
            )
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = item?.let {
        when (it.itemId) {
            R.id.action_add -> {
                startActivityForResult(
                    Intent(this, AddActivity::class.java)
                    , addActionRequestCode
                )
            }
        }
        true
    } ?: super.onOptionsItemSelected(item)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            addActionRequestCode -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.let {
                        addActionToTodoList(it.getParcelableExtra(resultInput))
                    }
                }
            }
        }
    }

    private fun addActionToTodoList(action: Action) {
        actions.add(action)
        updateCurrentFragment()
    }

    private fun updateCurrentFragment() {
        supportFragmentManager.findFragmentByTag(TodoFragment.todoFragmentTag).also {
            it?.let { fragment ->
                if (fragment.isVisible && fragment is TodoFragment) {
                    fragment.updateAdapterData(actions)
                }
            }
        }
    }

    companion object {
        const val resultInput = "String:Input"
    }
}
