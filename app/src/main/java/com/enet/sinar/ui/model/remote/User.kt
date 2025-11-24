package com.enet.sinar.ui.model

data class UserDto(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val phone: String,
    val profile_pic: String,
    val student_num: String,
    val sex: Boolean,
    val university_id: Int
)