package com.greenwoo.presentation.detail.user

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.base.FragmentAdapter
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.DetailUserBinding
import com.greenwoo.presentation.detail.list.elements.ElementsFragment.Companion.ITEM_COURSE
import com.greenwoo.presentation.detail.list.elements.ElementsFragment.Companion.ITEM_FOLDER
import com.greenwoo.presentation.detail.list.elements.ElementsFragment.Companion.ITEM_MODULE
import com.greenwoo.presentation.detail.list.elements.ElementsFragment.Companion.ITEM_USER
import com.greenwoo.presentation.host.profile.subscribe.SubscribeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class UserFragment : BaseFragment<DetailUserBinding, UserViewData, UserViewModel>() {

    var btn = true

    override val layoutId = R.layout.detail_user

    override val viewModelClass: KClass<UserViewModel> = UserViewModel::class

    override fun initView(
        binding: DetailUserBinding,
        viewData: UserViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) {
        super.initView(binding, viewData, savedInstanceState, viewLifecycleOwner)

        binding.btnSubscribe.setOnClickListener {
            if (btn) {
                binding.btnSubscribe.setBackgroundResource(R.drawable.bg_outline)
                binding.btnSubscribe.setText(R.string.you_subscribed)
                binding.btnSubscribe.setTextColor(resources.getColor(R.color.white))
                btn = false
            } else if (!btn) {
                binding.btnSubscribe.setBackgroundResource(R.drawable.bg_btn)
                binding.btnSubscribe.setText(R.string.subscribe)
                btn = true
            }
        }

        val adapter = FragmentAdapter(
            arrayOf(
                Pair(
                    getString(R.string.subscribed),
                    SubscribeFragment.newInstanceAllPrograms()
                ),
                Pair(getString(R.string.subscribers), SubscribeFragment.newInstance())
            ),
            childFragmentManager
        )
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun bindViewModel(viewModel: UserViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: UserViewModel.NavigationTarget) {
        when (target) {
            is UserViewModel.NavigationTarget.Back -> {
                findNavController().popBackStack()
            }
            is UserViewModel.NavigationTarget.ListModule -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(UserFragmentDirections.actionDetailFriendToListElements(ITEM_MODULE))
            }
            is UserViewModel.NavigationTarget.ListFolder -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(UserFragmentDirections.actionDetailFriendToListElements(ITEM_FOLDER))
            }
            is UserViewModel.NavigationTarget.ListCourse -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(UserFragmentDirections.actionDetailFriendToListElements(ITEM_COURSE))
            }
            is UserViewModel.NavigationTarget.ListFriend -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(UserFragmentDirections.actionDetailFriendToListElements(ITEM_USER))
            }
        }
    }
}