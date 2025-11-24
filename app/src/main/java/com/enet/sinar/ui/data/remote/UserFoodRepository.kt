package com.enet.sinar.ui.data

import com.enet.sinar.ui.api.ApiSinar
import com.enet.sinar.ui.model.remote.ErrorResponse
import com.enet.sinar.ui.model.remote.FoodDto
import com.enet.sinar.ui.model.remote.RestaurantDto
import com.enet.sinar.ui.model.remote.UserDto
import com.enet.sinar.ui.model.remote.UserFoodDto
import com.enet.sinar.ui.model.remote.UserFoodItemDto
import com.enet.sinar.ui.model.remote.UserFoodItemRequest
import com.google.gson.Gson
import java.io.IOException


class UserFoodRepository {
    private val gson = Gson()
    suspend fun markFoodAsUsed(id: Int): Result<String>{
        return try {
            val response = ApiSinar.apiUserFood.markUserFood(id)
            if (response.isSuccessful) {
                val message = response.body()?.message ?: "Marked successfully"
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

    suspend fun getActiveUserFood(): Result<List<UserFoodDto>> {
        return try {
            val response = ApiSinar.apiUserFood.getActiveUserFood()

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

    suspend fun createUserFood(userFood: UserFoodItemRequest): Result<List<UserFoodItemDto>> {
        return try {
            val response = ApiSinar.apiUserFood.createUserFood(userFood)

            if (response.isSuccessful) {
                val foodList = response.body()
                if (foodList != null) {
                    Result.success(foodList)
                } else {
                    Result.success(emptyList())
                }
            } else {
                val errorBodyString = response.errorBody()?.string()
                var errorMessage: String = ""
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

                Result.failure(Exception(errorMessage))
            }
        } catch (e: IOException) {
            //  خطای شبکه: مدیریت عدم دسترسی به اینترنت، TimeOut و ...
            Result.failure(Exception("خطای شبکه: اتصال برقرار نشد."))
        } catch (e: Exception) {
            //  سایر خطاهای ناشناخته
            Result.failure(e)
        }
    }

    suspend fun getUserFoodById(id: Int): Result<UserFoodDto> {
        return try {
            val response = ApiSinar.apiUserFood.getUserFoodById(id)

            if (response.isSuccessful) {
                val food = response.body()
                if (food != null) {
                    Result.success(food)
                } else {
                    val f = FoodDto(0,"")
                    val i = UserFoodItemDto(0,"","","",
                        0,0,0,0,0)
                    val r = RestaurantDto(0,"","",false,0)
                    val u = UserDto(0,"","","","",
                        "",false,0)
                    Result.success(UserFoodDto(f,i,r,u))
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
