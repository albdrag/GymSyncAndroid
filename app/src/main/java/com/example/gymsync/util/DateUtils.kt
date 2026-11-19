package com.example.gymsync.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Utility object for formatting dates and timestamps throughout GymSync.
 */
object DateUtils {

    private const val DEFAULT_DATE_FORMAT = "MMM dd, yyyy"

    /**
     * Formats an epoch timestamp in milliseconds to a human-readable date string.
     */
    fun formatDate(timeMillis: Long, formatPattern: String = DEFAULT_DATE_FORMAT): String {
        val formatter = SimpleDateFormat(formatPattern, Locale.getDefault())
        return formatter.format(Date(timeMillis))
    }
}
