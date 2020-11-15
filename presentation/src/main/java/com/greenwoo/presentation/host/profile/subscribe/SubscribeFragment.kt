package com.greenwoo.presentation.host.profile.subscribe

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.ProgramsListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class SubscribeFragment :
    BaseFragment<ProgramsListBinding, SubscribeViewData, SubscribeViewModel>() {

    companion object {
        const val isFriends = "isFriends"

        fun newInstanceAllPrograms() = SubscribeFragment().apply {
            val bundle = Bundle()
            bundle.putBoolean(isFriends, true)
            arguments = bundle
        }

        fun newInstance() = SubscribeFragment().apply {
            val bundle = Bundle()
            bundle.putBoolean(isFriends, false)
            arguments = bundle
        }
    }

    override val layoutId = R.layout.programs_list

    override val viewModelClass: KClass<SubscribeViewModel> = SubscribeViewModel::class

    override fun initView(
        binding: ProgramsListBinding,
        viewData: SubscribeViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) {
        super.initView(binding, viewData, savedInstanceState, viewLifecycleOwner)
        binding.rvList.adapter = SubscribeAdapter {
            viewData.onClickedDetail.invoke()
        }
    }

    override fun bindViewModel(viewModel: SubscribeViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: SubscribeViewModel.NavigationTarget) {
        when (target) {
            is SubscribeViewModel.NavigationTarget.Detail -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(R.id.global_action_to_detail_users)
            }
        }
    }
}