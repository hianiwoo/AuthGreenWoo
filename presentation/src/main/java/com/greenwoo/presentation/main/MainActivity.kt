package com.greenwoo.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseActivity
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.ActivityMainBinding
import com.greenwoo.presentation.host.profile.setting.SettingFragmentDirections
import com.greenwoo.presentation.host.profile.setting.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding, MainViewData, MainViewModel>() {

    override val layoutId = R.layout.activity_main

    override val viewModelClass = MainViewModel::class

    override fun bindViewModel(viewModel: MainViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: MainViewModel.NavigationTarget) {
        when (target) {
            is MainViewModel.NavigationTarget.ShowMessage -> {
                Toast.makeText(this, target.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}