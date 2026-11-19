package com.example.gymsync.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymsync.R
import com.example.gymsync.data.memory.MemoryDataRepository
import com.example.gymsync.domain.model.Exercise
import com.example.gymsync.domain.model.MuscleGroup
import com.example.gymsync.presentation.adapter.ExerciseAdapter
import com.example.gymsync.presentation.viewmodel.ExerciseViewModel
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Activity for displaying, searching, and managing exercises.
 */
class ExerciseListActivity : AppCompatActivity() {

    private lateinit var viewModel: ExerciseViewModel
    private lateinit var adapter: ExerciseAdapter
    private lateinit var etSearchExercise: EditText
    private lateinit var chipGroupCategories: ChipGroup
    private lateinit var rvExercises: RecyclerView
    private lateinit var fabAddExercise: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_list)

        viewModel = ExerciseViewModel(MemoryDataRepository(this))

        etSearchExercise = findViewById(R.id.etSearchExercise)
        chipGroupCategories = findViewById(R.id.chipGroupCategories)
        rvExercises = findViewById(R.id.rvExercises)
        fabAddExercise = findViewById(R.id.fabAddExercise)

        rvExercises.layoutManager = LinearLayoutManager(this)

        adapter = ExerciseAdapter(
            exercises = emptyList(),
            onEditClick = { exercise -> openEditExerciseScreen(exercise) },
            onDeleteClick = { exercise -> showDeleteConfirmationDialog(exercise) }
        )
        rvExercises.adapter = adapter

        fabAddExercise.setOnClickListener {
            val intent = Intent(this, AddEditExerciseActivity::class.java)
            startActivity(intent)
        }

        etSearchExercise.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filtered = viewModel.setSearchQuery(s?.toString() ?: "")
                adapter.updateData(filtered)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        chipGroupCategories.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val muscleGroup = when (checkedIds.first()) {
                    R.id.chipChest -> MuscleGroup.CHEST
                    R.id.chipLegs -> MuscleGroup.LEGS
                    R.id.chipBack -> MuscleGroup.BACK
                    R.id.chipAbs -> MuscleGroup.ABS
                    R.id.chipArms -> MuscleGroup.ARMS
                    R.id.chipShoulders -> MuscleGroup.SHOULDERS
                    R.id.chipCardio -> MuscleGroup.CARDIO
                    else -> MuscleGroup.ALL
                }
                val filtered = viewModel.setSelectedMuscleGroup(muscleGroup)
                adapter.updateData(filtered)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refreshExerciseList()
    }

    private fun refreshExerciseList() {
        val exercises = viewModel.getExercises()
        adapter.updateData(exercises)
    }

    private fun openEditExerciseScreen(exercise: Exercise) {
        val intent = Intent(this, AddEditExerciseActivity::class.java).apply {
            putExtra(EXTRA_EXERCISE_ID, exercise.id)
            putExtra(EXTRA_EXERCISE_NAME, exercise.name)
            putExtra(EXTRA_EXERCISE_DESC, exercise.description)
            putExtra(EXTRA_EXERCISE_SETS, exercise.sets)
            putExtra(EXTRA_EXERCISE_REPS, exercise.reps)
            putExtra(EXTRA_EXERCISE_WEIGHT, exercise.weightKg)
            putExtra(EXTRA_EXERCISE_CATEGORY, exercise.muscleGroup.name)
        }
        startActivity(intent)
    }

    private fun showDeleteConfirmationDialog(exercise: Exercise) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.dialog_title_delete))
            .setMessage(getString(R.string.dialog_message_delete))
            .setNegativeButton(getString(R.string.action_cancel), null)
            .setPositiveButton(getString(R.string.action_delete)) { _, _ ->
                viewModel.deleteExercise(exercise.id)
                refreshExerciseList()
                Toast.makeText(this, getString(R.string.toast_exercise_deleted), Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    companion object {
        const val EXTRA_EXERCISE_ID = "EXTRA_EXERCISE_ID"
        const val EXTRA_EXERCISE_NAME = "EXTRA_EXERCISE_NAME"
        const val EXTRA_EXERCISE_DESC = "EXTRA_EXERCISE_DESC"
        const val EXTRA_EXERCISE_SETS = "EXTRA_EXERCISE_SETS"
        const val EXTRA_EXERCISE_REPS = "EXTRA_EXERCISE_REPS"
        const val EXTRA_EXERCISE_WEIGHT = "EXTRA_EXERCISE_WEIGHT"
        const val EXTRA_EXERCISE_CATEGORY = "EXTRA_EXERCISE_CATEGORY"
    }
}
