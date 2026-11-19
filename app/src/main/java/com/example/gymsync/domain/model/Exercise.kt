package com.example.gymsync.domain.model

/**
 * Domain model representing a gym exercise.
 *
 * @property id Unique identifier of the exercise.
 * @property name Title of the exercise.
 * @property description Detailed instructions or notes for performing the exercise.
 * @property muscleGroup Targeted muscle category.
 * @property sets Number of sets recommended or logged.
 * @property reps Number of repetitions per set.
 * @property weightKg Weight in kilograms used for the exercise.
 * @property imageUrl Optional URL or drawable resource reference for exercise demonstration.
 */
data class Exercise(
    val id: String,
    val name: String,
    val description: String,
    val muscleGroup: MuscleGroup,
    val sets: Int,
    val reps: Int,
    val weightKg: Double,
    val imageUrl: String = ""
)
