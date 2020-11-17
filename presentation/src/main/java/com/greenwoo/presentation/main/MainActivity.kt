package com.greenwoo.presentation.main

import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseActivity
import com.greenwoo.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding, MainViewData, MainViewModel>() {

    override val layoutId = R.layout.activity_main

    override val viewModelClass = MainViewModel::class
}