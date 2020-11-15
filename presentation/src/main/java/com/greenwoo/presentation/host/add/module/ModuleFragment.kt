package com.greenwoo.presentation.host.add.module

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.auth.signup.SignUpFragmentDirections
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.AddModuleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class ModuleFragment : BaseFragment<AddModuleBinding, ModuleViewData, ModuleViewModel>() {

    override val layoutId = R.layout.add_module

    override val viewModelClass: KClass<ModuleViewModel> = ModuleViewModel::class

    override fun bindViewModel(viewModel: ModuleViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: ModuleViewModel.NavigationTarget) {
        when (target) {
            is ModuleViewModel.NavigationTarget.Back -> {
                findNavController().popBackStack()
            }
            is ModuleViewModel.NavigationTarget.CreateCard -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .popBackStack(R.id.host, false)
            }
        }
    }
}