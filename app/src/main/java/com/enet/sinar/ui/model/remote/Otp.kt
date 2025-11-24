package com.enet.sinar.ui.model

data class OtpDto(
    val phone: String
)

data class VerifyOtpDto(
    val otp: String,
    val phone: String
)