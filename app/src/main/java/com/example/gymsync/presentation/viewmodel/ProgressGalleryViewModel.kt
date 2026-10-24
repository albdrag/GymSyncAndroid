package com.example.gymsync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymsync.domain.model.ProgressPhoto
import com.example.gymsync.domain.repository.GymSyncRepository
import com.example.gymsync.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel managing progress photo gallery logs.
 */
class ProgressGalleryViewModel(
    private val repository: GymSyncRepository
) : ViewModel() {

    private val _photosState = MutableStateFlow<UiState<List<ProgressPhoto>>>(UiState.Loading)
    val photosState: StateFlow<UiState<List<ProgressPhoto>>> = _photosState.asStateFlow()

    init {
        loadPhotos()
    }

    fun loadPhotos() {
        viewModelScope.launch {
            _photosState.value = UiState.Loading
            try {
                repository.getProgressPhotos().collect { photos ->
                    _photosState.value = UiState.Success(photos)
                }
            } catch (e: Exception) {
                _photosState.value = UiState.Error(e.localizedMessage ?: "Failed to load progress photos.")
            }
        }
    }

    fun addPhoto(photo: ProgressPhoto) {
        viewModelScope.launch {
            try {
                repository.addProgressPhoto(photo)
                loadPhotos()
            } catch (e: Exception) {
                _photosState.value = UiState.Error(e.localizedMessage ?: "Failed to save progress photo.")
            }
        }
    }

    fun deletePhoto(photoId: String) {
        viewModelScope.launch {
            try {
                repository.deleteProgressPhoto(photoId)
                loadPhotos()
            } catch (e: Exception) {
                _photosState.value = UiState.Error(e.localizedMessage ?: "Failed to delete progress photo.")
            }
        }
    }
}
