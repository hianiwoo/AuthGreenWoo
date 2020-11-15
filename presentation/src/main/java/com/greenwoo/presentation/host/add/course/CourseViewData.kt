package com.greenwoo.presentation.host.add.course

import androidx.lifecycle.MutableLiveData

class CourseViewData(
    val onBackClicked: () -> Unit,
    val onClickCreateCourse: () -> Unit,
) {
    val key: MutableLiveData<String> = MutableLiveData()
    val uid: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val enableCreateCourse: MutableLiveData<Boolean> = MutableLiveData(false)
}