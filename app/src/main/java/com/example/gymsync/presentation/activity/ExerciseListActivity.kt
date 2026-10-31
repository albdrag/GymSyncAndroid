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
import com.example.gymsync.presentation.viewmodel.ExerciseViewModel
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Activity for displaying and searching the exercise library.
 */
class ExerciseListActivity : AppCompatActivity() {

    private lateinit var viewModel: ExerciseViewModel
    private lateinit var etSearchExercise: EditText
    private lateinit var chipGroupCategories: ChipGroup
    private lateinit var rvExercises: RecyclerView
    private lateinit var fabAddExercise: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_list)

        viewModel = ExerciseViewModel(MemoryDataRepository())

        etSearchExercise = findViewById(R.id.etSearchExercise)
        chipGroupCategories = findViewById(R.id.chipGroupCategories)
        rvExercises = findViewById(R.id.rvExercises)
        fabAddExercise = findViewById(R.id.fabAddExercise)

        rvExercises.layoutManager = LinearLayoutManager(this)

        fabAddExercise.setOnClickListener {
            val intent = Intent(this, AddEditExerciseActivity::class.java)
            startActivity(intent)
        }

        etSearchExercise.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setSearchQuery(s?.toString() ?: "")
                Toast.makeText(this@ExerciseListActivity, "Filtering by: " + (s ?: ""), Toast.LENGTH_SHORT).show()
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
                viewModel.setSelectedMuscleGroup(muscleGroup)
                Toast.makeText(this, "Selected category: " + muscleGroup.name, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
