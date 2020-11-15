package com.greenwoo.presentation.host.profile

import androidx.lifecycle.MutableLiveData
import com.greenwoo.domain.models.User

class ProfileViewData(
    val onListFriend: () -> Unit,
    val onSetting: () -> Unit,
    val onResume: () -> Unit
) {
    val listSubscribers = MutableLiveData<List<Int>>(arrayListOf(1, 2, 3, 4, 5, 6))
    val mail: MutableLiveData<String> = MutableLiveData()
    val nickname: MutableLiveData<String> = MutableLiveData()
    val url: MutableLiveData<String> = MutableLiveData()
    val user = MutableLiveData<User>()
}