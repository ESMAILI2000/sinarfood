package com.enet.sinar.ui.utility

import android.content.Context

object MySharedPreferences {
    private fun getSharedPreferences(context: Context) =
        context.getSharedPreferences("Sinar", Context.MODE_PRIVATE)

    fun setlang(context: Context,lang : String)=
        getSharedPreferences(context).edit().putString("app_lang",lang).apply()

    fun getLang(context: Context)=
        getSharedPreferences(context).getString("app_lang","fa")

    fun setUserType(context: Context,userType : String)=
        getSharedPreferences(context).edit().putString("user_type",userType).apply()

    fun getUserType(context: Context)=
        getSharedPreferences(context).getString("user_type","Student")

    fun setisLogin(context: Context,isLogin : Boolean)=
        getSharedPreferences(context).edit().putBoolean("is_login",isLogin).apply()

    fun getisLogin(context: Context)=
        getSharedPreferences(context).getBoolean("is_login",false)

    fun setFoodType(context: Context,foodType : String)=
        getSharedPreferences(context).edit().putString("food_type",foodType).apply()

    fun getFoodType(context: Context)=
        getSharedPreferences(context).getString("food_type","")

    fun setPickupLocation(context: Context,pickupLocation : String)=
        getSharedPreferences(context).edit().putString("pickup_location",pickupLocation).apply()

    fun getPickupLocation(context: Context)=
        getSharedPreferences(context).getString("pickup_location","")
}