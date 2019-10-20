package com.manubla.taskmanager.view.home.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manubla.taskmanager.R
import com.manubla.taskmanager.controller.AuthController
import com.manubla.taskmanager.view.auth.AuthActivity
import com.manubla.taskmanager.view.home.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.coroutines.CoroutineContext


class ProfileFragment : BaseFragment() , CoroutineScope {
    private val authController = AuthController()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ) = inflater.inflate(R.layout.fragment_profile, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logOut.setOnClickListener { logout() }
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

}
