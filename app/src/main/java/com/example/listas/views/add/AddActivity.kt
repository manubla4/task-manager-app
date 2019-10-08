package com.example.listas.views.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.listas.R
import com.example.listas.data.Action
import com.example.listas.views.todo.MainActivity
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity(), AddFragment.OnFragmentInteractionListener {

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
