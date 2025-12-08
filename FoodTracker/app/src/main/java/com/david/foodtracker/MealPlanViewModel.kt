package com.david.foodtracker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MealPlanViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    // Guardamos el "vigilante" para poder detenerlo si cerramos la app
    private var listenerRegistration: ListenerRegistration? = null

    var currentPlan by mutableStateOf<List<DailyMenu>>(emptyList())
        private set

    var userObjectiveName by mutableStateOf("Cargando...")
        private set

    init {
        startListeningUserPlan()
    }

    private fun startListeningUserPlan() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            currentPlan = MealPlanDataSource.getPlanForObjective(null)
            userObjectiveName = "General"
            return
        }

        // CAMBIO CLAVE: Usamos addSnapshotListener en lugar de get()
        // Esto escucha cambios en vivo. Si guardas en Objetivos, aquí se actualiza solo.
        listenerRegistration = db.collection("users").document(userId)
            .addSnapshotListener { document, error ->
                if (error != null || document == null || !document.exists()) {
                    // Si falla o no existe, ponemos el default
                    currentPlan = MealPlanDataSource.getPlanForObjective(null)
                    userObjectiveName = "General"
                    return@addSnapshotListener
                }

                // Si hubo cambios, actualizamos las variables automáticamente
                val objective = document.getString("objective")
                currentPlan = MealPlanDataSource.getPlanForObjective(objective)
                userObjectiveName = if (objective.isNullOrBlank()) "General" else objective
            }
    }

    // Cuando salimos de la app o esta pantalla muere, dejamos de escuchar para ahorrar batería
    override fun onCleared() {
        super.onCleared()
        listenerRegistration?.remove()
    }
}