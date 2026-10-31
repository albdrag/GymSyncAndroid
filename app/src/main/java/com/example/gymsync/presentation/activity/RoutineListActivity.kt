package com.example.gymsync.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymsync.R
import com.example.gymsync.data.memory.MemoryDataRepository
import com.example.gymsync.presentation.viewmodel.RoutineViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Activity for displaying workout routines.
 */
class RoutineListActivity : AppCompatActivity() {

    private lateinit var viewModel: RoutineViewModel
    private lateinit var rvRoutines: RecyclerView
    private lateinit var fabAddRoutine: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine_list)

        viewModel = RoutineViewModel(MemoryDataRepository())

        rvRoutines = findViewById(R.id.rvRoutines)
        fabAddRoutine = findViewById(R.id.fabAddRoutine)

        rvRoutines.layoutManager = LinearLayoutManager(this)

        fabAddRoutine.setOnClickListener {
            Toast.makeText(this, getString(R.string.routine_add_title), Toast.LENGTH_SHORT).show()
        }
    }
}
