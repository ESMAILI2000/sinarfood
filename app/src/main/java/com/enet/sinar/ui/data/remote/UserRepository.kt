package com.enet.sinar.ui.data

import com.enet.sinar.ui.api.ApiSinar
import com.enet.sinar.ui.model.remote.ErrorResponse
import com.enet.sinar.ui.model.remote.UniversityDto
import com.enet.sinar.ui.model.remote.UserDto
import com.enet.sinar.ui.model.remote.UserWithUniversity
import com.google.gson.Gson
import java.io.IOException

class UserRepository {
    private val gson = Gson()

    suspend fun getUser(userId: String): Result<UserWithUniversity> {
        return try {
            val response = ApiSinar.apiUser.getUserByStudentNumber(userId)

            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    Result.success(user)
                } else {
                    val u = UniversityDto(0,"","","")
                    val i = UserDto(0,"","","","","",false,0)
                    Result.success(UserWithUniversity(i,u))
                }
            } else {
                val errorBodyString = response.errorBody()?.string()
                val errorMessage: String
                if (errorBodyString != null) {
                    errorMessage = try {
                        val errorResponse = gson.fromJson(errorBodyString, ErrorResponse::class.java)
                        errorResponse.error
                    } catch (e: Exception) {
                        "خطای نامشخص (کد ${response.code()})"
                    }
                } else {
                    // اگر خطا خالی بود
                    "خطا در ارتباط با سرور (کد ${response.code()})"
                }

                Result.failure(Exception(errorBodyString))
            }
        } catch (e: IOException) {
            //  خطای شبکه: مدیریت عدم دسترسی به اینترنت، TimeOut و ...
            Result.failure(Exception("خطای شبکه: اتصال برقرار نشد."))
        } catch (e: Exception) {
            //  سایر خطاهای ناشناخته
            Result.failure(e)
        }
    }
}