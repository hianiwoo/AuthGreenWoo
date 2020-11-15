package com.greenwoo.presentation.host.add

class AddViewData(
    val onCreateModule: () -> Unit,
    val onCreateFolder: () -> Unit,
    val onCreateCourse: () -> Unit
)