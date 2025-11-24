package com.enet.sinar.ui.data

import com.enet.sinar.ui.api.ApiSinar
import com.enet.sinar.ui.model.remote.ErrorResponse
import com.enet.sinar.ui.model.remote.FoodDto
import com.google.gson.Gson
import java.io.IOException

class FoodRepository {
    private val gson = Gson()

    suspend fun getFoods(): Result<List<FoodDto>> {
        return try {
            val response = ApiSinar.apiFood.getFoods()

            if (response.isSuccessful) {
                val foodList = response.body()
                if (foodList != null) {
                    Result.success(foodList)
                } else {
                    Result.success(emptyList())
                }
            } else {
                val errorBodyString = response.errorBody()?.string()
                val errorMessage: String
                if (errorBodyString != null) {
                    errorMessage = try {
                        val errorResponse = gson.fromJson(errorBodyString, ErrorResponse::class.java)
                        errorResponse.error
                    } catch (e: Exception) {
                        "خطای نامشخص در دریافت لیست غذاها (کد ${response.code()})"
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