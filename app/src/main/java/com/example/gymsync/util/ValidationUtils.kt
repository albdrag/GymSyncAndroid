package com.example.gymsync.util

/**
 * Utility object for validating form inputs and domain data.
 */
object ValidationUtils {

    /**
     * Validates if a text field is non-empty after trimming.
     */
    fun isNotBlank(text: String?): Boolean {
        return !text.isNullOrBlank()
    }

    /**
     * Validates that a numeric value is strictly positive (> 0).
     */
    fun isPositiveNumber(value: Double): Boolean {
        return value > 0.0
    }

    /**
     * Validates that a set or repetition count is positive (> 0).
     */
    fun isPositiveInteger(value: Int): Boolean {
        return value > 0
    }
}
