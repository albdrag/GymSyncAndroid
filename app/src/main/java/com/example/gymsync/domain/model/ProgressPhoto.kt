package com.example.gymsync.domain.model

/**
 * Domain model representing a user's fitness progress photo log.
 *
 * @property id Unique identifier of the progress entry.
 * @property photoUri URI string pointing to the stored image (from camera or gallery).
 * @property weightKg User's recorded weight in kilograms at the time of the photo.
 * @property dateMillis Epoch timestamp in milliseconds when the photo entry was recorded.
 * @property notes Personal notes or comments on physical progress.
 */
data class ProgressPhoto(
    val id: String,
    val photoUri: String,
    val weightKg: Double,
    val dateMillis: Long = System.currentTimeMillis(),
    val notes: String = ""
)
