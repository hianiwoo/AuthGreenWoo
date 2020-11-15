package com.greenwoo.presentation.detail.course

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.base.FragmentAdapter
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.DetailCourseBinding
import com.greenwoo.presentation.host.search.modules.ModulesFragment
import com.greenwoo.presentation.host.search.users.UsersFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class CourseFragment : BaseFragment<DetailCourseBinding, CourseViewData, CourseViewModel>() {

    override val layoutId = R.layout.detail_course

    companion object {
        const val PROGRAM_KEY = "key"
    }

    private var onResume: () -> Unit = {}

    override val viewModelClass: KClass<CourseViewModel> = CourseViewModel::class

    override fun initView(
        binding: DetailCourseBinding,
        viewData: CourseViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) {
        super.initView(binding, viewData, savedInstanceState, viewLifecycleOwner)

        onResume = viewData.onResume

        val adapter = FragmentAdapter(
            arrayOf(
                Pair(getString(R.string.modules), ModulesFragment.newInstance()),
                Pair(getString(R.string.participants), UsersFragment.newInstance()),
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

    override fun bindViewModel(viewModel: CourseViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: CourseViewModel.NavigationTarget) {
        when (target) {
            is CourseViewModel.NavigationTarget.Back -> {
                findNavController().popBackStack()
            }
        }
    }
}