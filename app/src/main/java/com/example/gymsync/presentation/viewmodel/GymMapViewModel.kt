package com.example.gymsync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gymsync.domain.model.GymBranch
import com.example.gymsync.domain.repository.GymSyncRepository

/**
 * ViewModel managing physical gym branches.
 */
class GymMapViewModel(
    private val repository: GymSyncRepository
) : ViewModel() {

    fun getBranches(): List<GymBranch> {
        return repository.getGymBranches()
    }
}
