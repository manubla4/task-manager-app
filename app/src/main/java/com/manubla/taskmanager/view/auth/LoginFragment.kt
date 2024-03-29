package com.manubla.taskmanager.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.manubla.taskmanager.R
import com.manubla.taskmanager.controller.AuthController
import com.manubla.taskmanager.extension.editableTextString
import com.manubla.taskmanager.extension.gone
import com.manubla.taskmanager.extension.visible
import com.manubla.taskmanager.util.emailRegex
import com.manubla.taskmanager.util.showLongErrorMessage
import com.manubla.taskmanager.view.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class LoginFragment : Fragment(), CoroutineScope {

    private val authController = AuthController()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginLayout.requestFocus()
        loginButton.setOnClickListener { login() }
        signUpButton.setOnClickListener { goToSignUp() }
    }

    private fun goToSignUp() {
        fragmentManager?.let {
            it.beginTransaction()
            .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
            .replace(R.id.container, SignupFragment(), SignupFragment.FragmentTag)
            .addToBackStack(SignupFragment.FragmentTag)
            .commit()
        }
    }

    private fun startLoading(){
        loginButton.gone()
        loginProgress.visible()
    }
    private fun stopLoading(){
        loginProgress.gone()
        loginButton.visible()
    }

    private fun validateFields(email : String, password : String) : Boolean {
        emailInputLayout.error = null
        passwordInputLayout.error = null

        var result = true

        if (email.isBlank()) {
            emailInputLayout.error = getString(R.string.field_empty)
            result = false
        }
        else if (!emailRegex.matcher(email).matches()) {
            emailInputLayout.error = getString(R.string.auth_activity_wrong_format)
            result = false
        }

        if (password.isBlank()) {
            passwordInputLayout.error = getString(R.string.field_empty)
            result = false
        }

        return result
    }

    private fun login() {
        val email = emailEditText.editableTextString()
        val password = passwordEditText.editableTextString()

        if (validateFields(email, password)) {
            startLoading()
            launch(Dispatchers.IO) {
                try {
                    authController.login(email, password)
                    withContext(Dispatchers.Main) {
                        stopLoading()
                        activity?.let {
                            startActivity(Intent(it, HomeActivity::class.java))
                            it.finish()
                        }
                    }
                } catch (exception: Exception) {
                    withContext(Dispatchers.Main) {
                        stopLoading()
                        showLongErrorMessage(getString(R.string.auth_activity_login_error), view, activity)
                    }
                }
            }
        }
    }

    companion object {
        const val FragmentTag = "LoginFragmentTag"
    }
}
