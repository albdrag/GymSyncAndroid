package com.example.gymsync.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymsync.R
import com.example.gymsync.domain.model.Exercise
import com.google.android.material.chip.Chip

/**
 * RecyclerView Adapter for displaying exercises in the library list.
 */
class ExerciseAdapter(
    private var exercises: List<Exercise>,
    private val onEditClick: (Exercise) -> Unit,
    private val onDeleteClick: (Exercise) -> Unit
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    fun updateData(newExercises: List<Exercise>) {
        exercises = newExercises
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(exercises[position], onEditClick, onDeleteClick)
    }

    override fun getItemCount(): Int = exercises.size

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvExerciseName: TextView = itemView.findViewById(R.id.tvExerciseName)
        private val chipCategory: Chip = itemView.findViewById(R.id.chipCategory)
        private val tvExerciseDescription: TextView = itemView.findViewById(R.id.tvExerciseDescription)
        private val tvSetsReps: TextView = itemView.findViewById(R.id.tvSetsReps)
        private val tvWeight: TextView = itemView.findViewById(R.id.tvWeight)
        private val btnEditExercise: Button = itemView.findViewById(R.id.btnEditExercise)
        private val btnDeleteExercise: Button = itemView.findViewById(R.id.btnDeleteExercise)

        fun bind(
            exercise: Exercise,
            onEditClick: (Exercise) -> Unit,
            onDeleteClick: (Exercise) -> Unit
        ) {
            val context = itemView.context
            tvExerciseName.text = exercise.name
            chipCategory.text = exercise.muscleGroup.name
            tvExerciseDescription.text = exercise.description

            val setsRepsText = exercise.sets.toString() + " sets x " + exercise.reps + " reps"
            tvSetsReps.text = setsRepsText

            val weightText = exercise.weightKg.toString() + " kg"
            tvWeight.text = weightText

            btnEditExercise.setOnClickListener { onEditClick(exercise) }
            btnDeleteExercise.setOnClickListener { onDeleteClick(exercise) }
        }
    }
}
