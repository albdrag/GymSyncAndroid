package com.example.gymsync.domain.model

/**
 * Domain model representing user profile and fitness dashboard status.
 *
 * @property id Unique user identifier.
 * @property name User's full name.
 * @property email User's primary email address.
 * @property streakDays Current consecutive workout streak count.
 * @property activeRoutineName Name of the active routine for today's workout.
 */
data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val streakDays: Int,
    val activeRoutineName: String
)
