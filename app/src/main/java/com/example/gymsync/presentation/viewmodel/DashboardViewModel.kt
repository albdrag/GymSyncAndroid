package com.example.gymsync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymsync.domain.model.UserProfile
import com.example.gymsync.domain.repository.GymSyncRepository
import com.example.gymsync.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel managing main user dashboard status, profile, and streak data.
 */
class DashboardViewModel(
    private val repository: GymSyncRepository
) : ViewModel() {

    private val _userProfileState = MutableStateFlow<UiState<UserProfile>>(UiState.Loading)
    val userProfileState: StateFlow<UiState<UserProfile>> = _userProfileState.asStateFlow()

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            _userProfileState.value = UiState.Loading
            try {
                repository.getUserProfile().collect { profile ->
                    _userProfileState.value = UiState.Success(profile)
                }
            } catch (e: Exception) {
                _userProfileState.value = UiState.Error(e.localizedMessage ?: "Failed to load user profile.")
            }
        }
    }

    fun incrementStreak() {
        viewModelScope.launch {
            val currentState = _userProfileState.value
            if (currentState is UiState.Success) {
                val updatedProfile = currentState.data.copy(
                    streakDays = currentState.data.streakDays + 1
                )
                repository.updateUserProfile(updatedProfile)
                loadUserProfile()
            }
        }
    }
}
