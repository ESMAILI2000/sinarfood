package com.enet.sinar.ui.data

import com.enet.sinar.ui.api.ApiSinar
import com.enet.sinar.ui.model.remote.ErrorResponse
import com.enet.sinar.ui.model.remote.UniversityDto
import com.google.gson.Gson
import java.io.IOException

class UniversityRepository {
    private val gson = Gson()

    suspend fun getUniversity(universityId: Int): Result<UniversityDto> {
        return try {
            val response = ApiSinar.apiUniversity.getUniversityById(universityId)

            if (response.isSuccessful) {
                val university = response.body()
                if (university != null) {
                    Result.success(university)
                } else {
                    Result.success(UniversityDto(0,"","",""))
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