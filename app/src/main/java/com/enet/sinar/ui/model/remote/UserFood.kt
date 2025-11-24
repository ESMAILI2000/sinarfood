package com.enet.sinar.ui.model

data class UserFoodItemRequest(
    val code: String,
    val expiration_hours: Int,
    val food_id: Int,
    val price: Int,
    val Restaurant_id: Int,
    val sinar_price: Int,
    val user_id: Int,
)

data class UserFoodItemDto(
    val id: Int,
    val created_at: String,
    val code: String,
    val expires_at: String,
    val Restaurant_id: Int,
    val food_id: Int,
    val price: Int,
    val sinar_price: Int,
    val user_id: Int,
)

data class UserFoodDto(
    val food: FoodDto,
    val info: UserFoodItemDto,
    val restaurant: RestaurantDto,
    val user: UserDto
)