package com.david.foodtracker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ObjectivesViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    // Campos de estado
    var selectedObjective by mutableStateOf<String?>(null)
    var currentWeight by mutableStateOf("")
    var targetWeight by mutableStateOf("")
    var age by mutableStateOf("")

    var isLoading by mutableStateOf(false)
        private set

    var message by mutableStateOf<String?>(null)
        private set

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        val userId = auth.currentUser?.uid ?: return
        isLoading = true

        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Si el campo no existe o está vacío, se queda como null
                    val obj = document.getString("objective")
                    selectedObjective = if (obj.isNullOrBlank()) null else obj

                    currentWeight = document.getString("currentWeight") ?: ""
                    targetWeight = document.getString("targetWeight") ?: ""
                    age = document.getString("age") ?: ""
                }
                isLoading = false
            }
            .addOnFailureListener { isLoading = false }
    }

    // --- NUEVA FUNCIÓN: TOGGLE (ACTIVAR/DESACTIVAR) ---
    fun toggleObjective(objective: String) {
        if (selectedObjective == objective) {
            // Si ya estaba seleccionado, lo quitamos (null)
            selectedObjective = null
        } else {
            // Si era otro o ninguno, lo seleccionamos
            selectedObjective = objective
        }
    }

    fun saveAllData() {
        val userId = auth.currentUser?.uid ?: return
        isLoading = true

        // Ya no bloqueamos si selectedObjective es null.
        // Guardamos "" si es null para limpiar la base de datos.
        val data = hashMapOf(
            "objective" to (selectedObjective ?: ""),
            "currentWeight" to currentWeight,
            "targetWeight" to targetWeight,
            "age" to age
        )

        db.collection("users").document(userId)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                isLoading = false
                message = "¡Datos actualizados correctamente!"
            }
            .addOnFailureListener {
                isLoading = false
                message = "Error al guardar. Intenta de nuevo."
            }
    }

    fun clearMessage() {
        message = null
    }
}