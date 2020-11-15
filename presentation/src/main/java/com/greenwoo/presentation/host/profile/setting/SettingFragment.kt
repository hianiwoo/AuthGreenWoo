package com.greenwoo.presentation.host.profile.setting

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.auth.signup.SignUpViewModel
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.FileUtil
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.ProfileSettingBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import kotlin.reflect.KClass

@AndroidEntryPoint
class SettingFragment : BaseFragment<ProfileSettingBinding, SettingViewData, SettingViewModel>() {

    override val layoutId = R.layout.profile_setting

    var onActivityResultFile: ((File) -> Unit)? = null

    override val viewModelClass: KClass<SettingViewModel> = SettingViewModel::class

    override fun initView(
        binding: ProfileSettingBinding,
        viewData: SettingViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) {
        super.initView(binding, viewData, savedInstanceState, viewLifecycleOwner)
        onActivityResultFile = viewData.onActivityResultFile
        binding.tvChangeAvatar.setOnClickListener {
            openFileChooser()
        }
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "select picture"), SELECT_A_PHOTO)
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_A_PHOTO && resultCode == RESULT_OK && data != null && data.data != null) {
            val filePath = data.data ?: return
            try {
                onActivityResultFile?.invoke(FileUtil.from(requireContext(), filePath))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun bindViewModel(viewModel: SettingViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: SettingViewModel.NavigationTarget) {
        when (target) {
            is SettingViewModel.NavigationTarget.Back -> {
                findNavController().popBackStack()
            }
            is SettingViewModel.NavigationTarget.SignOut -> {
                findNavController().navigate(SettingFragmentDirections.actionProfileSettingToSignIn())
            }
            is SettingViewModel.NavigationTarget.Profile -> {
                requireActivity().findNavController(R.id.mainNavHost)
                    .navigate(R.id.global_action_to_menu_profile)
            }
            is SettingViewModel.NavigationTarget.ShowMessage -> {
                Toast.makeText(requireContext(), target.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val SELECT_A_PHOTO = 1554
    }
}