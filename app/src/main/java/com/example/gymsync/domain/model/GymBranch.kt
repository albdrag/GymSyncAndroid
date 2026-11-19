package com.example.gymsync.domain.model

/**
 * Domain model representing a physical gym branch location.
 *
 * @property id Unique identifier of the gym branch.
 * @property name Branch name (e.g., "GymSync Central").
 * @property address Physical street address.
 * @property latitude GPS latitude coordinate.
 * @property longitude GPS longitude coordinate.
 * @property distanceKm Estimated distance in kilometers from user location.
 * @property isOpen Flag indicating if the branch is currently open.
 * @property openingHours Human-readable operating schedule.
 */
data class GymBranch(
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val distanceKm: Double,
    val isOpen: Boolean,
    val openingHours: String
)
