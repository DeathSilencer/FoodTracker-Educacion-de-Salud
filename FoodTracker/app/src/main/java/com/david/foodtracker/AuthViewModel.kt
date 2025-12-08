package com.david.foodtracker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val db: FirebaseFirestore = Firebase.firestore

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Función para limpiar el error cuando se cierra el diálogo
    fun clearError() {
        errorMessage = null
    }

    fun register(email: String, pass: String, name: String, onSuccess: () -> Unit) {
        if (email.isBlank() || pass.isBlank() || name.isBlank()) {
            errorMessage = "Por favor completa todos los campos."
            return
        }

        isLoading = true
        errorMessage = null

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    val userMap = hashMapOf(
                        "uid" to userId,
                        "name" to name,
                        "email" to email
                    )
                    if (userId != null) {
                        db.collection("users").document(userId).set(userMap)
                            .addOnSuccessListener {
                                isLoading = false
                                onSuccess()
                            }
                            .addOnFailureListener {
                                isLoading = false
                                onSuccess()
                            }
                    }
                } else {
                    isLoading = false
                    // Traducimos el error técnico a español
                    errorMessage = mapFirebaseError(task.exception)
                }
            }
    }

    fun login(email: String, pass: String, onSuccess: () -> Unit) {
        if (email.isBlank() || pass.isBlank()) {
            errorMessage = "Ingresa tu correo y contraseña."
            return
        }

        isLoading = true
        errorMessage = null

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    errorMessage = mapFirebaseError(task.exception)
                }
            }
    }

    // --- TRADUCTOR DE ERRORES ---
    private fun mapFirebaseError(exception: Exception?): String {
        return when (exception) {
            is FirebaseAuthInvalidCredentialsException -> "Correo o contraseña incorrectos."
            is FirebaseAuthInvalidUserException -> "No existe una cuenta con este correo."
            is FirebaseAuthUserCollisionException -> "Este correo ya está registrado."
            is FirebaseAuthWeakPasswordException -> "La contraseña debe tener al menos 6 caracteres."
            is FirebaseNetworkException -> "Error de conexión. Revisa tu internet."
            else -> "Ocurrió un error inesperado. Intenta de nuevo."
        }
    }
}