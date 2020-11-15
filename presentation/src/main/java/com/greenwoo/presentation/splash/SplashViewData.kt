package com.greenwoo.presentation.splash

class SplashViewData(val onAnimationEnd: () -> Unit) {
    var isEndAnimated: Boolean = false
    var isAuth: Boolean? = null
}