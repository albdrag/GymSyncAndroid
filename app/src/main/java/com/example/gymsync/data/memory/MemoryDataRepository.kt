package com.example.gymsync.data.memory

import android.content.Context
import com.example.gymsync.domain.model.Exercise
import com.example.gymsync.domain.model.GymBranch
import com.example.gymsync.domain.model.MuscleGroup
import com.example.gymsync.domain.model.ProgressPhoto
import com.example.gymsync.domain.model.Routine
import com.example.gymsync.domain.model.UserProfile
import com.example.gymsync.domain.repository.GymSyncRepository
import org.json.JSONObject
import java.util.UUID

/**
 * In-memory implementation of [GymSyncRepository].
 * Loads initial data from a JSON file in assets to avoid hardcoded data.
 */
class MemoryDataRepository(context: Context? = null) : GymSyncRepository {

    private val exercisesList = mutableListOf<Exercise>()
    private val routinesList = mutableListOf<Routine>()
    private val progressPhotosList = mutableListOf<ProgressPhoto>()
    private val gymBranchesList = mutableListOf<GymBranch>()
    private var userProfile = UserProfile("u1", "Jordan Alban Alvarez Vega", "jordan@gymsync.com", 5, "Legs & Core")

    init {
        context?.let { loadDataFromJson(it) }
    }

    private fun loadDataFromJson(context: Context) {
        try {
            val jsonString = context.assets.open("gymsync_data.json").bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)

            if (jsonObject.has("userProfile")) {
                val userObj = jsonObject.getJSONObject("userProfile")
                userProfile = UserProfile(
                    id = userObj.optString("id", "u1"),
                    name = userObj.optString("name", "User"),
                    email = userObj.optString("email", ""),
                    streakDays = userObj.optInt("streakDays", 0),
                    activeRoutineName = userObj.optString("activeRoutineName", "Legs & Core")
                )
            }

            if (jsonObject.has("exercises")) {
                val exercisesArray = jsonObject.getJSONArray("exercises")
                for (i in 0 until exercisesArray.length()) {
                    val exObj = exercisesArray.getJSONObject(i)
                    val groupStr = exObj.optString("muscleGroup", "ALL")
                    val muscleGroup = try { MuscleGroup.valueOf(groupStr) } catch (e: Exception) { MuscleGroup.ALL }

                    exercisesList.add(
                        Exercise(
                            id = exObj.optString("id", UUID.randomUUID().toString()),
                            name = exObj.optString("name", ""),
                            description = exObj.optString("description", ""),
                            muscleGroup = muscleGroup,
                            sets = exObj.optInt("sets", 3),
                            reps = exObj.optInt("reps", 10),
                            weightKg = exObj.optDouble("weightKg", 0.0),
                            imageUrl = exObj.optString("imageUrl", "")
                        )
                    )
                }
            }

            if (jsonObject.has("routines")) {
                val routinesArray = jsonObject.getJSONArray("routines")
                for (i in 0 until routinesArray.length()) {
                    val rObj = routinesArray.getJSONObject(i)
                    routinesList.add(
                        Routine(
                            id = rObj.optString("id", UUID.randomUUID().toString()),
                            name = rObj.optString("name", ""),
                            description = rObj.optString("description", ""),
                            exercises = exercisesList.take(2),
                            difficultyLevel = rObj.optString("difficultyLevel", "Intermediate")
                        )
                    )
                }
            }

            if (jsonObject.has("progressPhotos")) {
                val photosArray = jsonObject.getJSONArray("progressPhotos")
                for (i in 0 until photosArray.length()) {
                    val pObj = photosArray.getJSONObject(i)
                    progressPhotosList.add(
                        ProgressPhoto(
                            id = pObj.optString("id", UUID.randomUUID().toString()),
                            photoUri = pObj.optString("photoUri", ""),
                            weightKg = pObj.optDouble("weightKg", 0.0),
                            notes = pObj.optString("notes", "")
                        )
                    )
                }
            }

            if (jsonObject.has("gymBranches")) {
                val branchesArray = jsonObject.getJSONArray("gymBranches")
                for (i in 0 until branchesArray.length()) {
                    val gObj = branchesArray.getJSONObject(i)
                    gymBranchesList.add(
                        GymBranch(
                            id = gObj.optString("id", UUID.randomUUID().toString()),
                            name = gObj.optString("name", ""),
                            address = gObj.optString("address", ""),
                            latitude = gObj.optDouble("latitude", 0.0),
                            longitude = gObj.optDouble("longitude", 0.0),
                            distanceKm = gObj.optDouble("distanceKm", 0.0),
                            isOpen = gObj.optBoolean("isOpen", true),
                            openingHours = gObj.optString("openingHours", "")
                        )
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // --- Exercises ---
    override fun getExercises(): List<Exercise> = exercisesList.toList()

    override fun getExerciseById(id: String): Exercise? {
        return exercisesList.find { it.id == id }
    }

    override fun addExercise(exercise: Exercise) {
        val newExercise = if (exercise.id.isBlank()) exercise.copy(id = UUID.randomUUID().toString()) else exercise
        exercisesList.add(newExercise)
    }

    override fun updateExercise(exercise: Exercise) {
        val index = exercisesList.indexOfFirst { it.id == exercise.id }
        if (index != -1) {
            exercisesList[index] = exercise
        }
    }

    override fun deleteExercise(id: String) {
        exercisesList.removeAll { it.id == id }
    }

    override fun searchExercises(query: String, muscleGroup: MuscleGroup): List<Exercise> {
        return exercisesList.filter { exercise ->
            val matchesQuery = query.isBlank() ||
                    exercise.name.contains(query, ignoreCase = true) ||
                    exercise.description.contains(query, ignoreCase = true)
            val matchesCategory = muscleGroup == MuscleGroup.ALL || exercise.muscleGroup == muscleGroup
            matchesQuery && matchesCategory
        }
    }

    // --- Routines ---
    override fun getRoutines(): List<Routine> = routinesList.toList()

    override fun getRoutineById(id: String): Routine? {
        return routinesList.find { it.id == id }
    }

    override fun addRoutine(routine: Routine) {
        val newRoutine = if (routine.id.isBlank()) routine.copy(id = UUID.randomUUID().toString()) else routine
        routinesList.add(newRoutine)
    }

    override fun updateRoutine(routine: Routine) {
        val index = routinesList.indexOfFirst { it.id == routine.id }
        if (index != -1) {
            routinesList[index] = routine
        }
    }

    override fun deleteRoutine(id: String) {
        routinesList.removeAll { it.id == id }
    }

    // --- Progress Photos ---
    override fun getProgressPhotos(): List<ProgressPhoto> = progressPhotosList.toList()

    override fun addProgressPhoto(photo: ProgressPhoto) {
        val newPhoto = if (photo.id.isBlank()) photo.copy(id = UUID.randomUUID().toString()) else photo
        progressPhotosList.add(newPhoto)
    }

    override fun deleteProgressPhoto(id: String) {
        progressPhotosList.removeAll { it.id == id }
    }

    // --- Gym Branches ---
    override fun getGymBranches(): List<GymBranch> = gymBranchesList.toList()

    override fun getGymBranchById(id: String): GymBranch? {
        return gymBranchesList.find { it.id == id }
    }

    // --- User Profile ---
    override fun getUserProfile(): UserProfile = userProfile

    override fun updateUserProfile(profile: UserProfile) {
        userProfile = profile
    }
}
