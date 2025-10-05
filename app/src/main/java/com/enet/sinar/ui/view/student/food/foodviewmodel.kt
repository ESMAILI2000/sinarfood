package com.enet.sinar.ui.view.student.food

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class Foodviewmodel : ViewModel() {


    var FoodType = mutableStateOf("")
    var PickupLocation = mutableStateOf("")

}