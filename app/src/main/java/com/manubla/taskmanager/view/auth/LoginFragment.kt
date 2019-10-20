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

    private fun login() {
        val email = emailEditText.editableTextString()
        val password = passwordEditText.editableTextString()

        if (email.isBlank() || password.isBlank()) {
            if (email.isBlank())
                emailInputLayout.error = getString(R.string.field_empty)
            if (password.isBlank())
                passwordInputLayout.error = getString(R.string.field_empty)
        }
        else {
            emailInputLayout.error = null
            passwordInputLayout.error = null

            launch(Dispatchers.IO) {
                try {
                    authController.login(email, password)
                    withContext(Dispatchers.Main) {
                        activity?.let {
                            startActivity(Intent(it, HomeActivity::class.java))
                            it.finish()
                        }
                    }
                } catch (exception: Exception) {
                    showLongErrorMessage(getString(R.string.service_error), activity)
                }
            }
        }
    }

    companion object {
        const val FragmentTag = "LoginFragmentTag"
    }
}
