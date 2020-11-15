package com.greenwoo.presentation.host.home

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.HostHomeBinding
import com.greenwoo.presentation.detail.course.CourseFragment
import com.greenwoo.presentation.detail.folder.FolderFragment
import com.greenwoo.presentation.detail.list.elements.ElementsFragment
import com.greenwoo.presentation.detail.module.ModuleFragment
import com.greenwoo.presentation.detail.user.UserFragmentDirections
import com.greenwoo.presentation.host.home.adapter.CourseAdapter
import com.greenwoo.presentation.host.home.adapter.FolderAdapter
import com.greenwoo.presentation.host.home.adapter.ModuleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class HomeFragment : BaseFragment<HostHomeBinding, HomeViewData, HomeViewModel>() {

    override val layoutId = R.layout.host_home

    private var onResume: () -> Unit = {}

    override val viewModelClass: KClass<HomeViewModel> = HomeViewModel::class

    override fun initView(
        binding: HostHomeBinding,
        viewData: HomeViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) {
        super.initView(binding, viewData, savedInstanceState, viewLifecycleOwner)

        onResume = viewData.onResume

        val adapterModule = ModuleAdapter { viewData.onClickedDetailModule.invoke(it) }

        binding.rvModule.adapter = adapterModule
        binding.rvModule.layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                lp.width = (width * 0.7).toInt()
                return true
            }
        }

        viewData.modules.observe(this) { adapterModule.setModule(it) }

        val adapterFolder = FolderAdapter { viewData.onClickedDetailFolder.invoke(it) }

        binding.rvFolder.adapter = adapterFolder
        binding.rvFolder.layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                lp.width = (width * 0.7).toInt()
                return true
            }
        }

        viewData.folders.observe(this) { adapterFolder.setFolder(it) }

        val adapterCourse = CourseAdapter { viewData.onClickedDetailCourse.invoke(it) }

        binding.rvCourse.adapter = adapterCourse
        binding.rvCourse.layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                lp.width = (width * 0.7).toInt()
                return true
            }
        }

        viewData.courses.observe(this) { adapterCourse.setCourse(it) }
    }

    override fun onResume() {
        super.onResume()
        onResume.invoke()
    }

    override fun bindViewModel(viewModel: HomeViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: HomeViewModel.NavigationTarget) {
        when (target) {
            is HomeViewModel.NavigationTarget.ListModule -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(UserFragmentDirections.globalActionToList(ElementsFragment.ITEM_MODULE))
            }
            is HomeViewModel.NavigationTarget.ListFolder -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(UserFragmentDirections.globalActionToList(ElementsFragment.ITEM_FOLDER))
            }
            is HomeViewModel.NavigationTarget.ListCourse -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(UserFragmentDirections.globalActionToList(ElementsFragment.ITEM_COURSE))
            }
            is HomeViewModel.NavigationTarget.DetailModule -> {
                val bundle = Bundle()
                bundle.putString(ModuleFragment.PROGRAM_KEY, target.module.key)
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(R.id.global_action_to_detail_module, bundle)
            }
            is HomeViewModel.NavigationTarget.DetailFolder -> {
                val bundle = Bundle()
                bundle.putString(FolderFragment.PROGRAM_KEY, target.folder.key)
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(R.id.global_action_to_detail_folder, bundle)
            }
            is HomeViewModel.NavigationTarget.DetailCourse -> {
                val bundle = Bundle()
                bundle.putString(CourseFragment.PROGRAM_KEY, target.course.key)
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(R.id.global_action_to_detail_course, bundle)
            }
        }
    }
}