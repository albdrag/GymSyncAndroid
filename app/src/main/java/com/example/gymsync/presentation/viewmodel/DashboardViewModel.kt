package com.example.gymsync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gymsync.domain.model.UserProfile
import com.example.gymsync.domain.repository.GymSyncRepository

/**
 * ViewModel managing main user dashboard status.
 */
class DashboardViewModel(
    private val repository: GymSyncRepository
) : ViewModel() {

    fun getUserProfile(): UserProfile {
        return repository.getUserProfile()
    }

    fun incrementStreak(): UserProfile {
        val current = repository.getUserProfile()
        val updated = current.copy(streakDays = current.streakDays + 1)
        repository.updateUserProfile(updated)
        return updated
    }
}
