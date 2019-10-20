package com.manubla.taskmanager.view.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.manubla.taskmanager.R
import kotlinx.android.synthetic.main.layout_toolbar.*

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, LoginFragment(), LoginFragment.FragmentTag)
                .commit()
        }
    }
}