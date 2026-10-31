package com.example.gymsync.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymsync.R
import com.example.gymsync.data.memory.MemoryDataRepository
import com.example.gymsync.presentation.viewmodel.GymMapViewModel

/**
 * Activity for displaying gym branches on map and location list.
 */
class GymMapActivity : AppCompatActivity() {

    private lateinit var viewModel: GymMapViewModel
    private lateinit var rvBranches: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gym_map)

        viewModel = GymMapViewModel(MemoryDataRepository())

        rvBranches = findViewById(R.id.rvBranches)
        rvBranches.layoutManager = LinearLayoutManager(this)

        Toast.makeText(this, getString(R.string.map_title) + " Loaded", Toast.LENGTH_SHORT).show()
    }
}
