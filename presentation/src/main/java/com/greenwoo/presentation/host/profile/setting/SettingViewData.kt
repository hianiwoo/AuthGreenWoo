package com.greenwoo.presentation.host.profile.setting

import androidx.lifecycle.MutableLiveData
import com.greenwoo.domain.models.User
import java.io.File

class SettingViewData(
    val onClickSignOut: () -> Unit,
    val onClickBack: () -> Unit,
    val onClickProfile: () -> Unit,
    val onActivityResultFile: (File) -> Unit
) {
    val user = MutableLiveData<User>()
    val url: MutableLiveData<String> = MutableLiveData()
    val mail: MutableLiveData<String> = MutableLiveData()
    val nickname: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val enableComplete: MutableLiveData<Boolean> = MutableLiveData(false)
}