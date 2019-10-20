package com.manubla.taskmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manubla.taskmanager.controller.RetrofitController
import com.manubla.taskmanager.view.auth.AuthActivity
import com.manubla.taskmanager.view.home.HomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val intent = when {
            RetrofitController.accessToken != null -> Intent(this, HomeActivity::class.java)
            else -> Intent(this, AuthActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}

