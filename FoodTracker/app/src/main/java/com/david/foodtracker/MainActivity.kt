package com.david.foodtracker // <--- ACTUALIZADO A FOODTRACKER

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppEducativaTheme {
                // Aquí inicia toda la app
                AppNavigationControl()
            }
        }
    }
}

// Configuración básica del tema
@Composable
fun AppEducativaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = PrimaryOrange,
            background = BackgroundBeige,
            onBackground = TextBrown
        ),
        content = content
    )
}