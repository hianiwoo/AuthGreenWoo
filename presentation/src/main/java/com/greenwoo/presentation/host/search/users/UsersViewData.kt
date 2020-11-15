package com.greenwoo.presentation.host.search.users

import androidx.lifecycle.MutableLiveData

class UsersViewData(
    val onClickedDetailUser: () -> Unit
) {
    val listUsers = MutableLiveData<List<Int>>(arrayListOf(1))
}