package com.example.gymsync.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymsync.R
import com.example.gymsync.data.memory.MemoryDataRepository
import com.example.gymsync.presentation.viewmodel.ProgressGalleryViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Activity for displaying progress gallery photos.
 */
class ProgressGalleryActivity : AppCompatActivity() {

    private lateinit var viewModel: ProgressGalleryViewModel
    private lateinit var rvGallery: RecyclerView
    private lateinit var fabAddPhoto: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_gallery)

        viewModel = ProgressGalleryViewModel(MemoryDataRepository())

        rvGallery = findViewById(R.id.rvGallery)
        fabAddPhoto = findViewById(R.id.fabAddPhoto)

        rvGallery.layoutManager = GridLayoutManager(this, 2)

        fabAddPhoto.setOnClickListener {
            Toast.makeText(this, getString(R.string.gallery_add_photo), Toast.LENGTH_SHORT).show()
        }
    }
}
