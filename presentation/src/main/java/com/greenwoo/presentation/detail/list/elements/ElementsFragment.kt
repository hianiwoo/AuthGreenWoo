package com.greenwoo.presentation.detail.list.elements

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.greenwoo.domain.models.Course
import com.greenwoo.domain.models.Folder
import com.greenwoo.domain.models.Module
import com.greenwoo.domain.models.User
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.ListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class ElementsFragment : BaseFragment<ListBinding, ElementsViewData, ElementsViewModel>() {

    override val layoutId = R.layout.list

    companion object {
        const val ITEM_MODULE = 0
        const val ITEM_FOLDER = 1
        const val ITEM_COURSE = 2
        const val ITEM_USER = 3
    }

    private var onResume: () -> Unit = {}

    override val viewModelClass: KClass<ElementsViewModel> = ElementsViewModel::class

    override fun initView(
        binding: ListBinding,
        viewData: ElementsViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) {
        super.initView(binding, viewData, savedInstanceState, viewLifecycleOwner)

        onResume = viewData.onResume

        binding.tvTitle.setText(R.string.modules)

        val adapter = ElementsAdapter {
            when (it) {
                is Module -> viewData.onClickedDetailModule.invoke()
                is Folder -> viewData.onClickedDetailFolder.invoke()
                is Course -> viewData.onClickedDetailCourse.invoke()
                is User -> viewData.onClickedDetailUsers.invoke()
                else -> return@ElementsAdapter
            }
        }

        viewData.type.observe(this) {
            adapter.type = it
            binding.rvList.adapter = adapter
        }

        viewData.modules.observe(this) { adapter.listModules = it }

        viewData.folders.observe(this) { adapter.listFolders = it }

        viewData.courses.observe(this) { adapter.listCourses = it }

        viewData.users.observe(this) { adapter.listUsers = it }
    }

    override fun onResume() {
        super.onResume()
        onResume.invoke()
    }

    override fun bindViewModel(viewModel: ElementsViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: ElementsViewModel.NavigationTarget) {
        when (target) {
            is ElementsViewModel.NavigationTarget.Back -> {
                findNavController().popBackStack()
            }
            is ElementsViewModel.NavigationTarget.DetailModule -> {
                findNavController().navigate(ElementsFragmentDirections.actionListToDetailModule())
            }
            is ElementsViewModel.NavigationTarget.DetailFolder -> {
                findNavController().navigate(ElementsFragmentDirections.actionListToDetailFolder())
            }
            is ElementsViewModel.NavigationTarget.DetailCourse -> {
                findNavController().navigate(ElementsFragmentDirections.actionListToDetailCourse())
            }
            is ElementsViewModel.NavigationTarget.DetailUsers -> {
                findNavController().navigate(ElementsFragmentDirections.actionListToDetailUser())
            }

        }
    }
}