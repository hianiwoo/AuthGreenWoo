package com.greenwoo.presentation.host

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.HostBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class HostFragment : BaseFragment<HostBinding, HostViewData, HostViewModel>() {

    override val layoutId = R.layout.host

    override val viewModelClass: KClass<HostViewModel> = HostViewModel::class

    override fun initView(
        binding: HostBinding,
        viewData: HostViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) {
        super.initView(binding, viewData, savedInstanceState, viewLifecycleOwner)
        val navController =
            childFragmentManager.findFragmentById(R.id.containerHost) as NavHostFragment
        binding.btmNavigation.setupWithNavController(navController.navController)
    }

    override fun bindViewModel(viewModel: HostViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: HostViewModel.NavigationTarget) {}

}
