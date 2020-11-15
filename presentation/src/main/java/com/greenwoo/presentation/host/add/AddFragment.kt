package com.greenwoo.presentation.host.add

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.HostAddBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class AddFragment : BaseFragment<HostAddBinding, AddViewData, AddViewModel>() {

    override val layoutId = R.layout.host_add

    override val viewModelClass: KClass<AddViewModel> = AddViewModel::class

    override fun bindViewModel(viewModel: AddViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: AddViewModel.NavigationTarget) {
        when (target) {
            is AddViewModel.NavigationTarget.Module -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(R.id.global_action_to_add_module)
                findNavController().navigate(R.id.global_action_to_menu_home)
            }
            is AddViewModel.NavigationTarget.Folder -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(R.id.global_action_to_add_folder)
                findNavController().navigate(R.id.global_action_to_menu_home)
            }
            is AddViewModel.NavigationTarget.Course -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(R.id.global_action_to_add_course)
                findNavController().navigate(R.id.global_action_to_menu_home)
            }

        }
    }
}