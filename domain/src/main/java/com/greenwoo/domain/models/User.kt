package com.greenwoo.domain.models

import java.io.Serializable

data class User(
    val id: String,
    val nickname: String,
    val url: String,
    val mail: String
) : Serializable

