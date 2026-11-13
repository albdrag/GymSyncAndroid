package com.example.gymsync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gymsync.domain.model.ProgressPhoto
import com.example.gymsync.domain.repository.GymSyncRepository

/**
 * ViewModel managing progress photo gallery logs.
 */
class ProgressGalleryViewModel(
    private val repository: GymSyncRepository
) : ViewModel() {

    fun getProgressPhotos(): List<ProgressPhoto> {
        return repository.getProgressPhotos()
    }

    fun addPhoto(photo: ProgressPhoto) {
        repository.addProgressPhoto(photo)
    }

    fun deletePhoto(photoId: String) {
        repository.deleteProgressPhoto(photoId)
    }
}
