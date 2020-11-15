package com.greenwoo.presentation.detail.list.elements

import androidx.lifecycle.MutableLiveData
import com.greenwoo.domain.models.Course
import com.greenwoo.domain.models.Folder
import com.greenwoo.domain.models.Module
import com.greenwoo.domain.models.User

class ElementsViewData (
    val title: MutableLiveData<Int>,
    val type: MutableLiveData<Int>,
    val onBackClicked: () -> Unit,
    val onClickedDetailModule: () -> Unit,
    val onClickedDetailFolder: () -> Unit,
    val onClickedDetailCourse: () -> Unit,
    val onClickedDetailUsers: () -> Unit,
    val onResume: () -> Unit
) {
    val loading = MutableLiveData(true)
    val folders: MutableLiveData<List<Folder>> = MutableLiveData()
    val modules: MutableLiveData<List<Module>> = MutableLiveData()
    val courses: MutableLiveData<List<Course>> = MutableLiveData()
    val users: MutableLiveData<List<User>> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val nickname: MutableLiveData<String> = MutableLiveData()
    val url: MutableLiveData<String> = MutableLiveData()
}