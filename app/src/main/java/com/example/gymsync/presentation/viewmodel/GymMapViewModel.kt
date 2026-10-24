package com.example.gymsync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymsync.domain.model.GymBranch
import com.example.gymsync.domain.repository.GymSyncRepository
import com.example.gymsync.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel managing physical gym branches and map view locations.
 */
class GymMapViewModel(
    private val repository: GymSyncRepository
) : ViewModel() {

    private val _branchesState = MutableStateFlow<UiState<List<GymBranch>>>(UiState.Loading)
    val branchesState: StateFlow<UiState<List<GymBranch>>> = _branchesState.asStateFlow()

    private val _selectedBranch = MutableStateFlow<GymBranch?>(null)
    val selectedBranch: StateFlow<GymBranch?> = _selectedBranch.asStateFlow()

    init {
        loadBranches()
    }

    fun loadBranches() {
        viewModelScope.launch {
            _branchesState.value = UiState.Loading
            try {
                repository.getGymBranches().collect { branches ->
                    _branchesState.value = UiState.Success(branches)
                    if (_selectedBranch.value == null && branches.isNotEmpty()) {
                        _selectedBranch.value = branches.first()
                    }
                }
            } catch (e: Exception) {
                _branchesState.value = UiState.Error(e.localizedMessage ?: "Failed to load gym branches.")
            }
        }
    }

    fun selectBranch(branch: GymBranch) {
        _selectedBranch.value = branch
    }
}
