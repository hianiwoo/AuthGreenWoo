package com.greenwoo.presentation.detail.folder

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.DetailFolderBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class FolderFragment : BaseFragment<DetailFolderBinding, FolderViewData, FolderViewModel>() {

    override val layoutId = R.layout.detail_folder

    companion object {
        const val PROGRAM_KEY = "key"
    }

    private var onResume: () -> Unit = {}

    override val viewModelClass: KClass<FolderViewModel> = FolderViewModel::class

    override fun initView(
        binding: DetailFolderBinding,
        viewData: FolderViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) {
        super.initView(binding, viewData, savedInstanceState, viewLifecycleOwner)

        onResume = viewData.onResume
    }

    override fun onResume() {
        super.onResume()
        onResume.invoke()
    }

    override fun bindViewModel(viewModel: FolderViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: FolderViewModel.NavigationTarget) {
        when (target) {
            is FolderViewModel.NavigationTarget.Back -> {
                findNavController().popBackStack()
            }
        }
    }
}