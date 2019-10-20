package com.manubla.taskmanager.view.auth

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
import com.manubla.taskmanager.util.showLongSuccessMessage
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SignupFragment : Fragment(), CoroutineScope {

    private val authController = AuthController()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_signup, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpLayout.requestFocus()
        signUpButton.setOnClickListener { signup() }
    }

    private fun startLoading(){
        signUpButton.gone()
        signUpProgress.visible()
    }
    private fun stopLoading(){
        signUpProgress.gone()
        signUpButton.visible()
    }

    private fun validateFields(name : String, email : String,
                               password : String, rePassword : String) : Boolean {
        nameInputLayout.error = null
        emailInputLayout.error = null
        passwordInputLayout.error = null
        repasswordInputLayout.error = null

        var result = true

        if (name.isBlank()) {
            nameInputLayout.error = getString(R.string.field_empty)
            result = false
        }

        if (email.isBlank()) {
            emailInputLayout.error = getString(R.string.field_empty)
            result = false
        }
        else if (!emailRegex.matcher(email).matches()) {
            emailInputLayout.error = getString(R.string.wrong_format)
            result = false
        }

        if (password.isBlank() || rePassword.isBlank()) {
            if (password.isBlank()) {
                passwordInputLayout.error = getString(R.string.field_empty)
                result = false
            }
            if (rePassword.isBlank()) {
                repasswordInputLayout.error = getString(R.string.field_empty)
                result = false
            }
        }
        else if (password != rePassword) {
            passwordInputLayout.error = getString(R.string.passwords_dont_match)
            repasswordInputLayout.error = getString(R.string.passwords_dont_match)
            result = false
        }

        return result
    }


    private fun signup() {
        val name = nameEditText.editableTextString()
        val email = emailEditText.editableTextString()
        val password = passwordEditText.editableTextString()
        val rePassword = rePasswordEditText.editableTextString()

        if (validateFields(name, email, password, rePassword)) {
            startLoading()
            launch(Dispatchers.IO) {
                try {
                    authController.signup(name, email, password, rePassword)
                    withContext(Dispatchers.Main) {
                        stopLoading()
                        showLongSuccessMessage(getString(R.string.signup_success), view, activity)
                        fragmentManager?.popBackStack()
                    }
                } catch (exception: Exception) {
                    withContext(Dispatchers.Main) {
                        stopLoading()
                        showLongErrorMessage(getString(R.string.signup_error), view, activity)
                    }
                }
            }
        }
    }

    companion object {
        const val FragmentTag = "SignupFragmentTag"
    }
}
