package com.greenwoo.presentation.host.profile

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.auth.signin.SignInViewModel
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.base.FragmentAdapter
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.HostProfileBinding
import com.greenwoo.presentation.detail.list.elements.ElementsFragment
import com.greenwoo.presentation.detail.user.UserFragmentDirections
import com.greenwoo.presentation.host.profile.subscribe.SubscribeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class ProfileFragment : BaseFragment<HostProfileBinding, ProfileViewData, ProfileViewModel>() {

    override val layoutId = R.layout.host_profile

    private var onResume: () -> Unit = {}

    override val viewModelClass: KClass<ProfileViewModel> = ProfileViewModel::class

    override fun initView(
        binding: HostProfileBinding,
        viewData: ProfileViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) {
        super.initView(binding, viewData, savedInstanceState, viewLifecycleOwner)

        onResume = viewData.onResume

        val adapter = FragmentAdapter(
            arrayOf(
                Pair(
                    getString(R.string.you_subscribed),
                    SubscribeFragment.newInstanceAllPrograms()
                ),
                Pair(getString(R.string.subscribers), SubscribeFragment.newInstance())
            ),
            childFragmentManager
        )
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onResume() {
        super.onResume()
        onResume.invoke()
    }

    override fun bindViewModel(viewModel: ProfileViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: ProfileViewModel.NavigationTarget) {
        when (target) {
            is ProfileViewModel.NavigationTarget.ListFriend -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(UserFragmentDirections.globalActionToList(ElementsFragment.ITEM_USER))
            }
            is ProfileViewModel.NavigationTarget.Setting -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(R.id.global_action_to_profile_setting)
                findNavController().navigate(R.id.global_action_to_menu_profile)
            }
        }
    }
}