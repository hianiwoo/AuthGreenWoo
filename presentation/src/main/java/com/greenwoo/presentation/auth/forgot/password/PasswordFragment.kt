package com.greenwoo.presentation.auth.forgot.password

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.auth.forgot.information.InformationDialog
import com.greenwoo.presentation.auth.signup.SignUpViewModel
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.ForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class PasswordFragment :
    BaseFragment<ForgotPasswordBinding, PasswordViewData, PasswordViewModel>() {

    override val layoutId = R.layout.forgot_password

    override val viewModelClass: KClass<PasswordViewModel> = PasswordViewModel::class

    override fun bindViewModel(viewModel: PasswordViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: PasswordViewModel.NavigationTarget) {
        when (target) {
            is PasswordViewModel.NavigationTarget.Back -> {
                findNavController().popBackStack()
            }
            is PasswordViewModel.NavigationTarget.Information -> {
                InformationDialog(requireContext(), target.onClickConfirm)
            }
            is PasswordViewModel.NavigationTarget.SignIn -> {
                findNavController().navigate(PasswordFragmentDirections.actionForgotPasswordToSignIn())
            }
            is PasswordViewModel.NavigationTarget.ShowMessage -> {
                Toast.makeText(requireContext(), target.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}