package com.greenwoo.presentation.host.search

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.base.FragmentAdapter
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.HostSearchBinding
import com.greenwoo.presentation.host.search.courses.CoursesFragment
import com.greenwoo.presentation.host.search.modules.ModulesFragment
import com.greenwoo.presentation.host.search.users.UsersFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class SearchFragment : BaseFragment<HostSearchBinding, SearchViewData, SearchViewModel>() {

    override val layoutId = R.layout.host_search

    override val viewModelClass: KClass<SearchViewModel> = SearchViewModel::class

    override fun initView(
        binding: HostSearchBinding,
        viewData: SearchViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) {
        super.initView(binding, viewData, savedInstanceState, viewLifecycleOwner)

        val adapter = FragmentAdapter(
            arrayOf(
                Pair(getString(R.string.modules), ModulesFragment.newInstance()),
                Pair(getString(R.string.courses), CoursesFragment.newInstance()),
                Pair(getString(R.string.users), UsersFragment.newInstance()),
            ),
            childFragmentManager
        )
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun bindViewModel(viewModel: SearchViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: SearchViewModel.NavigationTarget) {}
}