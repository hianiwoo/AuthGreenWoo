package com.greenwoo.presentation.splash

import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.SplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashBinding, SplashViewData, SplashViewModel>() {

    override val layoutId = R.layout.splash

    override val viewModelClass: KClass<SplashViewModel> = SplashViewModel::class

    override fun initView(
        binding: SplashBinding,
        viewData: SplashViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) {
        super.initView(binding, viewData, savedInstanceState, viewLifecycleOwner)

        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {

            override fun onTransitionCompleted(p0: MotionLayout?, currentId: Int) {

                if (currentId == R.id.end) {
                    viewData.onAnimationEnd.invoke()
                }
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
        })

        binding.motionLayout.postDelayed({
            showAnimation(binding.motionLayout, R.id.start, R.id.end)
        }, 0)
    }

    private fun showAnimation(motionLayout: MotionLayout, start: Int, end: Int) {
        motionLayout.post {
            motionLayout.setTransition(start, end)
            motionLayout.setTransitionDuration(2000)
            motionLayout.transitionToEnd()
        }
    }

    override fun bindViewModel(viewModel: SplashViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: SplashViewModel.NavigationTarget) {
        when (target) {
            is SplashViewModel.NavigationTarget.SignIn -> {
                findNavController().navigate(SplashFragmentDirections.actionSplashToSignIn())
            }
            is SplashViewModel.NavigationTarget.Host -> {
                findNavController().navigate(SplashFragmentDirections.actionSplashToHost())
            }
        }
    }
}