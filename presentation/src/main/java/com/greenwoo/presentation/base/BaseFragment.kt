package com.greenwoo.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.LifecycleOwner
import com.greenwoo.presentation.BR
import kotlin.reflect.KClass

abstract class BaseFragment<TBinding : ViewDataBinding, TViewData : Any, TViewModel : BaseViewModel<TViewData>> :
    Fragment() {

    @get:LayoutRes
    abstract val layoutId: Int

    abstract val viewModelClass: KClass<TViewModel>

    /**
     * Use this to bind to ViewModel events
     */
    open fun bindViewModel(viewModel: TViewModel, savedInstanceState: Bundle?) = Unit

    /**
     * Use this to do any UI initialization.
     */
    open fun initView(
        binding: TBinding,
        viewData: TViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) = Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModel = createViewModelLazy(
            viewModelClass,
            { viewModelStore },
            { defaultViewModelProviderFactory }).value

        bindViewModel(viewModel, savedInstanceState)

        val binding = DataBindingUtil.inflate<TBinding>(inflater, layoutId, container, false)
        binding.setVariable(BR.model, viewModel.viewData)
        binding.lifecycleOwner = viewLifecycleOwner

        initView(binding, viewModel.viewData, savedInstanceState, viewLifecycleOwner)

        return binding.root
    }
}