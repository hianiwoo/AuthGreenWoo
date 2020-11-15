package com.greenwoo.presentation.host.home

import androidx.lifecycle.MutableLiveData
import com.greenwoo.domain.models.Course
import com.greenwoo.domain.models.Folder
import com.greenwoo.domain.models.Module

class HomeViewData(
    val onListModule: () -> Unit,
    val onListFolder: () -> Unit,
    val onListCourse: () -> Unit,
    val onClickedDetailModule: (Module) -> Unit,
    val onClickedDetailFolder: (Folder) -> Unit,
    val onClickedDetailCourse: (Course) -> Unit,
    val onResume: () -> Unit
) {
    val folders: MutableLiveData<List<Folder>> = MutableLiveData()
    val modules: MutableLiveData<List<Module>> = MutableLiveData()
    val courses: MutableLiveData<List<Course>> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val nickname: MutableLiveData<String> = MutableLiveData()
    val url: MutableLiveData<String> = MutableLiveData()
    val loading = MutableLiveData(true)
    var visibility = MutableLiveData(false)
}