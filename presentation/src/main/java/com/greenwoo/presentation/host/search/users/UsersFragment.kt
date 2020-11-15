package com.greenwoo.presentation.host.search.users

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.SearchUsersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class UsersFragment : BaseFragment<SearchUsersBinding, UsersViewData, UsersViewModel>() {

    override val layoutId = R.layout.search_users

    companion object {
        fun newInstance() = UsersFragment()
    }

    override val viewModelClass: KClass<UsersViewModel> = UsersViewModel::class

    override fun initView(
        binding: SearchUsersBinding,
        viewData: UsersViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) {
        super.initView(binding, viewData, savedInstanceState, viewLifecycleOwner)

        binding.rvList.adapter = UsersAdapter {
            viewData.onClickedDetailUser.invoke()
        }
    }

    override fun bindViewModel(viewModel: UsersViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: UsersViewModel.NavigationTarget) {
        when (target) {
            is UsersViewModel.NavigationTarget.DetailUser -> {
                findNavController().navigate(UsersFragmentDirections.searchUsersToDetailUser())
            }
        }
    }
}