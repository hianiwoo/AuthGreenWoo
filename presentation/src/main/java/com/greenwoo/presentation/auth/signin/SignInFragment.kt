package com.greenwoo.presentation.auth.signin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.auth.signup.SignUpFragmentDirections
import com.greenwoo.presentation.auth.signup.SignUpViewModel
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.SigninBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.signin.*
import kotlin.reflect.KClass

@AndroidEntryPoint
class SignInFragment : BaseFragment<SigninBinding, SignInViewData, SignInViewModel>() {

    override val layoutId = R.layout.signin

    override val viewModelClass: KClass<SignInViewModel> = SignInViewModel::class

    override fun bindViewModel(viewModel: SignInViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: SignInViewModel.NavigationTarget) {
        when (target) {
            is SignInViewModel.NavigationTarget.Host -> {
                findNavController().navigate(SignInFragmentDirections.actionSignInToHost())
            }
            is SignInViewModel.NavigationTarget.SignUp -> {
                findNavController().navigate(SignInFragmentDirections.actionSignInToSignUp())
            }
            is SignInViewModel.NavigationTarget.ForgotPassword -> {
                findNavController().navigate(SignInFragmentDirections.actionSignInToForgotPassword())
            }
            is SignInViewModel.NavigationTarget.ShowMessage -> {
                Toast.makeText(requireContext(), target.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}