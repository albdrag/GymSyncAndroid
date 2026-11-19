package com.example.gymsync.domain.model

/**
 * Domain model representing a workout routine composed of multiple exercises.
 *
 * @property id Unique identifier of the routine.
 * @property name Title of the routine (e.g., "Leg Day & Core").
 * @property description Overview of the routine objectives.
 * @property exercises List of exercises included in this routine.
 * @property difficultyLevel Difficulty rating (e.g., "Beginner", "Intermediate", "Advanced").
 */
data class Routine(
    val id: String,
    val name: String,
    val description: String,
    val exercises: List<Exercise>,
    val difficultyLevel: String = "Intermediate"
)
