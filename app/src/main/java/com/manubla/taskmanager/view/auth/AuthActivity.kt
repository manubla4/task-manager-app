package com.manubla.taskmanager.view.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.manubla.taskmanager.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, LoginFragment(), LoginFragment.FragmentTag)
                .commit()
        }
    }
}