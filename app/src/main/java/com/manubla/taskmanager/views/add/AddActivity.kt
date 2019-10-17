package com.manubla.taskmanager.views.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.manubla.taskmanager.R
import com.manubla.taskmanager.data.Action
import com.manubla.taskmanager.views.MainActivity
import com.manubla.taskmanager.views.BaseFragment
import kotlinx.android.synthetic.main.layout_toolbar.*

class AddActivity : AppCompatActivity(), BaseFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, AddFragment(), null)
            .commit()
    }

    override fun onFragmentInteraction(input: Action) {
        setResult(
            Activity.RESULT_OK,
            Intent(this, MainActivity::class.java)
                .putExtra(MainActivity.resultInput, input)
        )
        finish()
    }
}
