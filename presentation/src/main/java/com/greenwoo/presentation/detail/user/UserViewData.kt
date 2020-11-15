package com.greenwoo.presentation.detail.user

import androidx.lifecycle.MutableLiveData
import com.greenwoo.domain.models.User

class UserViewData(
    val onBackClicked: () -> Unit,
    val onListModule: () -> Unit,
    val onListFolder: () -> Unit,
    val onListCourse: () -> Unit,
    val onListFriend: () -> Unit
){
    val listSubscribers = MutableLiveData<List<Int>>(arrayListOf(1,2,3,4,5,6))
    val user = MutableLiveData<User>()
}