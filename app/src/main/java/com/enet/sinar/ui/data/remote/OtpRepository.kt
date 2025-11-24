package com.enet.sinar.ui.data

import com.enet.sinar.ui.api.ApiSinar
import com.enet.sinar.ui.model.remote.ErrorResponse
import com.enet.sinar.ui.model.remote.OtpDto
import com.enet.sinar.ui.model.remote.VerifyOtpDto
import com.google.gson.Gson

class OtpRepository {
    private val gson = Gson()

    suspend fun sendOtpCod(otp: OtpDto): Result<String>{
        return try {
            val response = ApiSinar.apiOtp.createOtp(otp)
            if (response.isSuccessful) {
                val message = response.body()?.message ?: "successfully"
                Result.success(message)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                Result.failure(Exception(errorResponse.error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verify(verifyOtp: VerifyOtpDto): Result<String>{
        return try {
            val response = ApiSinar.apiOtp.verifyOtp(verifyOtp)
            if (response.isSuccessful) {
                val message = response.body()?.message ?: "successfully"
                Result.success(message)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                Result.failure(Exception(errorResponse.error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}