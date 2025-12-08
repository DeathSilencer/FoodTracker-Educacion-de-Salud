package com.david.foodtracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val PrimaryOrange = Color(0xFFE67E46)
val BackgroundBeige = Color(0xFFFFF3E0)
val TextBrown = Color(0xFF5D4037)
val ButtonTextWhite = Color.White

@Composable
fun ErrorAlert(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(Icons.Default.Error, contentDescription = null, tint = PrimaryOrange) },
        title = { Text("Atención", fontWeight = FontWeight.Bold, color = TextBrown) },
        text = { Text(message, textAlign = TextAlign.Center) },
        confirmButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)) {
                Text("Entendido", color = Color.White)
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun CustomButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth(0.8f).padding(vertical = 8.dp).height(55.dp)
    ) {
        Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = ButtonTextWhite)
    }
}

// --- HEADER ACTUALIZADO: AHORA ACEPTA EL BOTÓN DE SALIR ---
@Composable
fun HeaderTitle(
    text: String,
    showLogout: Boolean = false, // Por defecto no mostramos el botón (útil para Login)
    onLogout: () -> Unit = {}
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryOrange)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Espacio para la barra de estado (hora, batería)
                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))

                // Caja del contenido del header (60dp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Título central
                    Text(
                        text = "SALUD Y ALIMENTACIÓN",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    // Botón de Salir (Alineado a la derecha)
                    if (showLogout) {
                        IconButton(
                            onClick = onLogout,
                            modifier = Modifier.align(Alignment.CenterEnd) // Pegado a la derecha
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "Cerrar Sesión",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            color = TextBrown,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

// ... (TextFields siguen igual) ...
@Composable
fun CustomTextField(value: String, onValueChange: (String) -> Unit, label: String, imeAction: ImeAction = ImeAction.Next, onAction: () -> Unit = {}) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange, label = { Text(label) }, singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = KeyboardActions(onNext = { onAction() }, onDone = { onAction() }),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryOrange, focusedLabelColor = PrimaryOrange, cursorColor = PrimaryOrange, focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
        modifier = Modifier.fillMaxWidth(0.8f).padding(vertical = 4.dp)
    )
}

@Composable
fun CustomPasswordField(value: String, onValueChange: (String) -> Unit, label: String, imeAction: ImeAction = ImeAction.Done, onAction: () -> Unit = {}) {
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value, onValueChange = onValueChange, label = { Text(label) }, singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = imeAction),
        keyboardActions = KeyboardActions(onNext = { onAction() }, onDone = { onAction() }),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            IconButton(onClick = { passwordVisible = !passwordVisible }) { Icon(imageVector = image, contentDescription = "Toggle password visibility") }
        },
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryOrange, focusedLabelColor = PrimaryOrange, cursorColor = PrimaryOrange, focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
        modifier = Modifier.fillMaxWidth(0.8f).padding(vertical = 4.dp)
    )
}