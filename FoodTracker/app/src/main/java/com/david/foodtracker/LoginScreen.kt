package com.david.foodtracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    authViewModel.errorMessage?.let { error ->
        ErrorAlert(message = error, onDismiss = { authViewModel.clearError() })
    }

    // Usamos Scaffold para que el contenido no se corte
    Scaffold(
        containerColor = PrimaryOrange // Fondo naranja completo
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom // Contenido abajo (tarjeta blanca)
        ) {

            // Título Superior (Sobre fondo naranja)
            Spacer(modifier = Modifier.height(60.dp))
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = "Bienvenido de nuevo",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(40.dp))

            // TARJETA BLANCA CON EL FORMULARIO
            Card(
                colors = CardDefaults.cardColors(containerColor = BackgroundBeige),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // Plano para unir
                modifier = Modifier.fillMaxWidth().weight(1f) // Ocupa el resto del espacio
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Iniciar Sesión",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextBrown,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    CustomTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Correo Electrónico",
                        imeAction = ImeAction.Next,
                        onAction = { focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Down) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomPasswordField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Contraseña",
                        imeAction = ImeAction.Done,
                        onAction = {
                            focusManager.clearFocus()
                            authViewModel.login(email, password) { onLoginSuccess() }
                        }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    if (authViewModel.isLoading) {
                        CircularProgressIndicator(color = PrimaryOrange)
                    } else {
                        Button(
                            onClick = { authViewModel.login(email, password) { onLoginSuccess() } },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth().height(56.dp)
                        ) {
                            Text("ENTRAR", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("¿No tienes cuenta? ", color = Color.Gray)
                        Text(
                            text = "Regístrate aquí",
                            color = PrimaryOrange,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { onNavigateToRegister() }
                        )
                    }
                }
            }
        }
    }
}