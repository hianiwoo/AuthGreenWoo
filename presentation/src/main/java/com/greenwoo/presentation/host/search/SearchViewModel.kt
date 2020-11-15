package com.greenwoo.presentation.host.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event

class SearchViewModel @ViewModelInject constructor() : BaseViewModel<SearchViewData>() {

    override val viewData by lazy { SearchViewData() }

    sealed class NavigationTarget

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}