package com.example.gymsync.presentation.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gymsync.R
import com.example.gymsync.data.memory.MemoryDataRepository
import com.example.gymsync.domain.model.Exercise
import com.example.gymsync.domain.model.MuscleGroup
import com.example.gymsync.presentation.viewmodel.ExerciseViewModel
import com.example.gymsync.util.ValidationUtils

/**
 * Activity for creating or editing an exercise entry.
 */
class AddEditExerciseActivity : AppCompatActivity() {

    private lateinit var viewModel: ExerciseViewModel
    private lateinit var etName: EditText
    private lateinit var etDescription: EditText
    private lateinit var spinnerMuscleGroup: Spinner
    private lateinit var etSets: EditText
    private lateinit var etReps: EditText
    private lateinit var etWeight: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_exercise)

        viewModel = ExerciseViewModel(MemoryDataRepository())

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

        btnCancel.setOnClickListener {
            Toast.makeText(this, getString(R.string.action_cancel), Toast.LENGTH_SHORT).show()
            finish()
        }

        btnSave.setOnClickListener {
            saveExercise()
        }
    }

    private fun saveExercise() {
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

        val newExercise = Exercise(
            id = "",
            name = name,
            description = desc,
            muscleGroup = category,
            sets = sets,
            reps = reps,
            weightKg = weight
        )

        viewModel.addExercise(newExercise)
        Toast.makeText(this, "Exercise saved successfully!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
