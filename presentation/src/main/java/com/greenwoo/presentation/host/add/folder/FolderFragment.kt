package com.greenwoo.presentation.host.add.folder

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.AddFolderBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class FolderFragment : BaseFragment<AddFolderBinding, FolderViewData, FolderViewModel>() {

    override val layoutId = R.layout.add_folder

    override val viewModelClass: KClass<FolderViewModel> = FolderViewModel::class

    override fun bindViewModel(viewModel: FolderViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: FolderViewModel.NavigationTarget) {
        when (target) {
            is FolderViewModel.NavigationTarget.Back -> {
                findNavController().popBackStack()
            }
            is FolderViewModel.NavigationTarget.CreateFolder -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .popBackStack(R.id.host, false)
            }
        }
    }
}