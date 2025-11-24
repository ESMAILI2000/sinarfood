package com.enet.sinar.ui.view.general.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enet.sinar.ui.data.UniversityRepository
import com.enet.sinar.ui.model.UniversityDto
import com.enet.sinar.ui.utility.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UniversityViewModel(
    private val repository: UniversityRepository = UniversityRepository()
) : ViewModel() {

    private val _universityState = MutableStateFlow<UiState<UniversityDto>>(UiState.Idle)
    val universityState: StateFlow<UiState<UniversityDto>> = _universityState.asStateFlow()

    fun fetchUniversity(universityId: Int) {
        viewModelScope.launch {

            val result = repository.getUniversity(universityId)

            _universityState.value = result.fold(
                onSuccess = { university ->
                    UiState.Success(university)
                },
                onFailure = { throwable ->
                    UiState.Error(throwable.message ?: "خطا در دریافت اطلاعات")
                }
            )
        }
    }
}