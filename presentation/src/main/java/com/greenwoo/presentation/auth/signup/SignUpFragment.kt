package com.greenwoo.presentation.auth.signup

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.auth.signin.SignInViewModel
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.SignupBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignupBinding, SignUpViewData, SignUpViewModel>() {

    override val layoutId = R.layout.signup

    override val viewModelClass: KClass<SignUpViewModel> = SignUpViewModel::class

    override fun bindViewModel(viewModel: SignUpViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: SignUpViewModel.NavigationTarget) {
        when (target) {
            is SignUpViewModel.NavigationTarget.Host -> {
                findNavController().navigate(SignUpFragmentDirections.actionSignUpToHost())
            }
            is SignUpViewModel.NavigationTarget.SignIn -> {
                findNavController().navigate(SignUpFragmentDirections.actionSignUpToSignIn())
            }
            is SignUpViewModel.NavigationTarget.ShowMessage -> {
                Toast.makeText(requireContext(), target.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}