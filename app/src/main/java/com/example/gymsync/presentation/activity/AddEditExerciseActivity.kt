package com.example.gymsync.presentation.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gymsync.R
import com.example.gymsync.data.memory.MemoryDataRepository
import com.example.gymsync.domain.model.Exercise
import com.example.gymsync.domain.model.MuscleGroup
import com.example.gymsync.presentation.viewmodel.ExerciseViewModel
import com.example.gymsync.util.ValidationUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Activity for creating or editing an exercise entry with confirmation dialogs.
 */
class AddEditExerciseActivity : AppCompatActivity() {

    private lateinit var viewModel: ExerciseViewModel
    private lateinit var tvFormTitle: TextView
    private lateinit var etName: EditText
    private lateinit var etDescription: EditText
    private lateinit var spinnerMuscleGroup: Spinner
    private lateinit var etSets: EditText
    private lateinit var etReps: EditText
    private lateinit var etWeight: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private var currentExerciseId: String? = null
    private var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_exercise)

        viewModel = ExerciseViewModel(MemoryDataRepository(this))

        tvFormTitle = findViewById(R.id.tvFormTitle)
        etName = findViewById(R.id.etName)
        etDescription = findViewById(R.id.etDescription)
        spinnerMuscleGroup = findViewById(R.id.spinnerMuscleGroup)
        etSets = findViewById(R.id.etSets)
        etReps = findViewById(R.id.etReps)
        etWeight = findViewById(R.id.etWeight)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        val categories = MuscleGroup.values().map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMuscleGroup.adapter = adapter

        checkIntentForEditMode(categories)

        btnCancel.setOnClickListener {
            Toast.makeText(this, getString(R.string.action_cancel), Toast.LENGTH_SHORT).show()
            finish()
        }

        btnSave.setOnClickListener {
            validateAndSave()
        }
    }

    private fun checkIntentForEditMode(categories: List<String>) {
        if (intent.hasExtra(ExerciseListActivity.EXTRA_EXERCISE_ID)) {
            isEditMode = true
            currentExerciseId = intent.getStringExtra(ExerciseListActivity.EXTRA_EXERCISE_ID)
            tvFormTitle.text = getString(R.string.exercise_edit_title)

            etName.setText(intent.getStringExtra(ExerciseListActivity.EXTRA_EXERCISE_NAME) ?: "")
            etDescription.setText(intent.getStringExtra(ExerciseListActivity.EXTRA_EXERCISE_DESC) ?: "")
            etSets.setText(intent.getIntExtra(ExerciseListActivity.EXTRA_EXERCISE_SETS, 3).toString())
            etReps.setText(intent.getIntExtra(ExerciseListActivity.EXTRA_EXERCISE_REPS, 10).toString())
            etWeight.setText(intent.getDoubleExtra(ExerciseListActivity.EXTRA_EXERCISE_WEIGHT, 0.0).toString())

            val categoryName = intent.getStringExtra(ExerciseListActivity.EXTRA_EXERCISE_CATEGORY) ?: ""
            val categoryIndex = categories.indexOf(categoryName)
            if (categoryIndex != -1) {
                spinnerMuscleGroup.setSelection(categoryIndex)
            }
        }
    }

    private fun validateAndSave() {
        val name = etName.text.toString().trim()
        val desc = etDescription.text.toString().trim()
        val setsStr = etSets.text.toString().trim()
        val repsStr = etReps.text.toString().trim()
        val weightStr = etWeight.text.toString().trim()

        if (!ValidationUtils.isNotBlank(name)) {
            etName.error = getString(R.string.val_field_required)
            Toast.makeText(this, getString(R.string.val_field_required), Toast.LENGTH_SHORT).show()
            return
        }

        val sets = setsStr.toIntOrNull() ?: 3
        val reps = repsStr.toIntOrNull() ?: 10
        val weight = weightStr.toDoubleOrNull() ?: 0.0

        val categoryName = spinnerMuscleGroup.selectedItem.toString()
        val category = try {
            MuscleGroup.valueOf(categoryName)
        } catch (e: Exception) {
            MuscleGroup.CHEST
        }

        if (isEditMode) {
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.dialog_title_update))
                .setMessage(getString(R.string.dialog_message_update))
                .setNegativeButton(getString(R.string.action_cancel), null)
                .setPositiveButton(getString(R.string.action_save)) { _, _ ->
                    performSave(currentExerciseId ?: "", name, desc, category, sets, reps, weight)
                }
                .show()
        } else {
            performSave("", name, desc, category, sets, reps, weight)
        }
    }

    private fun performSave(
        id: String,
        name: String,
        desc: String,
        category: MuscleGroup,
        sets: Int,
        reps: Int,
        weight: Double
    ) {
        val exercise = Exercise(
            id = id,
            name = name,
            description = desc,
            muscleGroup = category,
            sets = sets,
            reps = reps,
            weightKg = weight
        )

        if (isEditMode) {
            viewModel.updateExercise(exercise)
            Toast.makeText(this, getString(R.string.toast_exercise_updated), Toast.LENGTH_SHORT).show()
        } else {
            viewModel.addExercise(exercise)
            Toast.makeText(this, getString(R.string.toast_exercise_saved), Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}
