package com.greenwoo.presentation.host.add.course

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.AddCourseBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class CourseFragment : BaseFragment<AddCourseBinding, CourseViewData, CourseViewModel>() {

    override val layoutId = R.layout.add_course

    override val viewModelClass: KClass<CourseViewModel> = CourseViewModel::class

    override fun bindViewModel(viewModel: CourseViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: CourseViewModel.NavigationTarget) {
        when (target) {
            is CourseViewModel.NavigationTarget.Back -> {
                findNavController().popBackStack()
            }
            is CourseViewModel.NavigationTarget.CreateCourse -> {
                requireActivity().findNavController(R.id.mainNavHost).popBackStack(R.id.host, false)
            }
        }
    }
}