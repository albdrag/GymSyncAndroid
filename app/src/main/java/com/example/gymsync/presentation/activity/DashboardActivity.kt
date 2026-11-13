package com.example.gymsync.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gymsync.R
import com.example.gymsync.data.memory.MemoryDataRepository
import com.example.gymsync.presentation.viewmodel.DashboardViewModel

/**
 * Main activity representing the user dashboard and quick navigation hub.
 */
class DashboardActivity : AppCompatActivity() {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var tvGreeting: TextView
    private lateinit var tvStreak: TextView
    private lateinit var tvTodayRoutineTitle: TextView
    private lateinit var btnLogWorkout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        viewModel = DashboardViewModel(MemoryDataRepository(this))

        tvGreeting = findViewById(R.id.tvGreeting)
        tvStreak = findViewById(R.id.tvStreak)
        tvTodayRoutineTitle = findViewById(R.id.tvTodayRoutineTitle)
        btnLogWorkout = findViewById(R.id.btnLogWorkout)

        setupQuickNavigation()

        btnLogWorkout.setOnClickListener {
            val updatedProfile = viewModel.incrementStreak()
            Toast.makeText(this, "Workout logged! Streak increased to " + updatedProfile.streakDays + " days!", Toast.LENGTH_SHORT).show()
            updateDashboardUi()
        }

        updateDashboardUi()
    }

    private fun updateDashboardUi() {
        val profile = viewModel.getUserProfile()
        tvGreeting.text = getString(R.string.dashboard_greeting, profile.name)
        tvStreak.text = getString(R.string.dashboard_streak, profile.streakDays)
        tvTodayRoutineTitle.text = getString(R.string.dashboard_today_routine, profile.activeRoutineName)
    }

    private fun setupQuickNavigation() {
        findViewById<android.view.View>(R.id.cardNavExercises).setOnClickListener {
            startActivity(Intent(this, ExerciseListActivity::class.java))
        }

        findViewById<android.view.View>(R.id.cardNavRoutines).setOnClickListener {
            startActivity(Intent(this, RoutineListActivity::class.java))
        }

        findViewById<android.view.View>(R.id.cardNavGallery).setOnClickListener {
            startActivity(Intent(this, ProgressGalleryActivity::class.java))
        }

        findViewById<android.view.View>(R.id.cardNavMap).setOnClickListener {
            startActivity(Intent(this, GymMapActivity::class.java))
        }
    }
}
