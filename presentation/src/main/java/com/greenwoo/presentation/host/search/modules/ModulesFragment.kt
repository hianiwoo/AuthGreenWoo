package com.greenwoo.presentation.host.search.modules

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.SearchModuleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class ModulesFragment : BaseFragment<SearchModuleBinding, ModulesViewData, ModulesViewModel>() {

    companion object {
        fun newInstance() = ModulesFragment()
    }

    override val layoutId = R.layout.search_module

    override val viewModelClass: KClass<ModulesViewModel> = ModulesViewModel::class

    override fun initView(
        binding: SearchModuleBinding,
        viewData: ModulesViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) {
        super.initView(binding, viewData, savedInstanceState, viewLifecycleOwner)

        binding.rvList.adapter = ModulesAdapter {
            viewData.onClickedDetailModule.invoke()
        }
    }

    override fun bindViewModel(viewModel: ModulesViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: ModulesViewModel.NavigationTarget) {
        when (target) {
            is ModulesViewModel.NavigationTarget.DetailModule -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(R.id.global_action_to_detail_module)
                findNavController().navigate(ModulesFragmentDirections.searchModulesToDetailModule())
            }
        }
    }
}