package com.david.foodtracker

import androidx.compose.foundation.background // <--- ESTA FALTABA
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showSuccessScreen by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    authViewModel.errorMessage?.let { error ->
        ErrorAlert(message = error, onDismiss = { authViewModel.clearError() })
    }

    if (showSuccessScreen) {
        // PANTALLA DE ÉXITO
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundBeige), // Ahora sí funcionará
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Éxito",
                    tint = PrimaryOrange,
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "¡Cuenta Creada!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextBrown,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Bienvenido, $name",
                    fontSize = 20.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(40.dp))

                CustomButton(text = "INICIAR SESIÓN") { onNavigateToLogin() }
            }
        }
    } else {
        // FORMULARIO DE REGISTRO
        Scaffold(containerColor = PrimaryOrange) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                Icon(
                    imageVector = Icons.Default.PersonAdd,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(80.dp)
                )

                Text(
                    text = "Únete a nosotros",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(30.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = BackgroundBeige),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Crear Cuenta",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextBrown,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        CustomTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = "Nombre Completo",
                            imeAction = ImeAction.Next,
                            onAction = { focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Down) }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        CustomTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = "Correo Electrónico",
                            imeAction = ImeAction.Next,
                            onAction = { focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Down) }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        CustomPasswordField(
                            value = password,
                            onValueChange = { password = it },
                            label = "Contraseña",
                            imeAction = ImeAction.Done,
                            onAction = {
                                focusManager.clearFocus()
                                authViewModel.register(email, password, name) { showSuccessScreen = true }
                            }
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        if (authViewModel.isLoading) {
                            CircularProgressIndicator(color = PrimaryOrange)
                        } else {
                            Button(
                                onClick = {
                                    authViewModel.register(email, password, name) { showSuccessScreen = true }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                            ) {
                                Text("REGISTRARSE", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        TextButton(onClick = onNavigateToLogin) {
                            Text("Volver al Login", color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}