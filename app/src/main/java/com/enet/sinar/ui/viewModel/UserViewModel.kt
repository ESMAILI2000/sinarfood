package com.enet.sinar.ui.view.student.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enet.sinar.ui.data.UserRepository
import com.enet.sinar.ui.model.UniversityDto
import com.enet.sinar.ui.model.UserWithUniversity
import com.enet.sinar.ui.utility.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    private val _userState = MutableStateFlow<UiState<UserWithUniversity>>(UiState.Idle)
    val userState: StateFlow<UiState<UserWithUniversity>> = _userState.asStateFlow()

    fun fetchUser(userId: Int) {
        viewModelScope.launch {

            val result = repository.getUser(userId)

            _userState.value = result.fold(
                onSuccess = { user ->
                    UiState.Success(user)
                },
                onFailure = { throwable ->
                    UiState.Error(throwable.message ?: "خطا در دریافت اطلاعات")
                }
            )
        }
    }

}