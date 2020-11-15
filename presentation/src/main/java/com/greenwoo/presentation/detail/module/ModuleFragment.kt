package com.greenwoo.presentation.detail.module

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.DetailModuleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class ModuleFragment : BaseFragment<DetailModuleBinding, ModuleViewData, ModuleViewModel>() {

    override val layoutId = R.layout.detail_module

    companion object {
        fun newInstance() = ModuleFragment()
        const val PROGRAM_KEY = "key"
    }

    private var onResume: () -> Unit = {}

    override val viewModelClass: KClass<ModuleViewModel> = ModuleViewModel::class

    override fun initView(
        binding: DetailModuleBinding,
        viewData: ModuleViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) {
        super.initView(binding, viewData, savedInstanceState, viewLifecycleOwner)

        onResume = viewData.onResume
    }

    override fun onResume() {
        super.onResume()
        onResume.invoke()
    }

    override fun bindViewModel(viewModel: ModuleViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: ModuleViewModel.NavigationTarget) {
        when (target) {
            is ModuleViewModel.NavigationTarget.Back -> {
                findNavController().popBackStack()
            }
        }
    }
}