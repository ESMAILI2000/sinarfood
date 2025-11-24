package com.enet.sinar.ui.data

import com.enet.sinar.ui.api.ApiSinar
import com.enet.sinar.ui.model.remote.ErrorResponse
import com.enet.sinar.ui.model.remote.RestaurantDto
import com.google.gson.Gson
import java.io.IOException

class RestaurantRepository {
    private val gson = Gson()

    suspend fun getRestaurants(universityId: Int): Result<List<RestaurantDto>> {
        return try {
            val response = ApiSinar.apiRestaurant.getRestaurantByUniversityId(universityId)

            if (response.isSuccessful) {
                val restaurantList = response.body()
                if (restaurantList != null) {
                    Result.success(restaurantList)
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