package com.greenwoo.presentation.host.search.courses

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.SearchCourseBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class CoursesFragment : BaseFragment<SearchCourseBinding, CoursesViewData, CoursesViewModel>() {

    companion object {
        fun newInstance() = CoursesFragment()
    }

    override val layoutId = R.layout.search_course

    override val viewModelClass: KClass<CoursesViewModel> = CoursesViewModel::class

    override fun initView(
        binding: SearchCourseBinding,
        viewData: CoursesViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) {
        super.initView(binding, viewData, savedInstanceState, viewLifecycleOwner)

        binding.rvList.adapter = CoursesAdapter {
            viewData.onClickedDetailCourse.invoke()
        }
    }

    override fun bindViewModel(viewModel: CoursesViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: CoursesViewModel.NavigationTarget) {
        when (target) {
            is CoursesViewModel.NavigationTarget.DetailCourse -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(R.id.global_action_to_detail_course)
                findNavController().navigate(CoursesFragmentDirections.searchCoursesToDetailCourse())
            }
        }
    }

}