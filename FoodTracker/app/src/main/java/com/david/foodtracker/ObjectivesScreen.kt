package com.david.foodtracker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ObjectivesScreen(
    onLogout: () -> Unit, // <--- ¡ESTE ES EL QUE FALTABA!
    viewModel: ObjectivesViewModel = viewModel()
) {
    viewModel.message?.let { msg ->
        AlertDialog(
            onDismissRequest = { viewModel.clearMessage() },
            title = { Text("Información", color = TextBrown, fontWeight = FontWeight.Bold) },
            text = { Text(msg) },
            confirmButton = { Button(onClick = { viewModel.clearMessage() }, colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)) { Text("Aceptar", color = Color.White) } },
            containerColor = Color.White, shape = RoundedCornerShape(16.dp)
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderTitle(text = "TU PERFIL Y METAS", showLogout = true, onLogout = onLogout)

        Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
            Text(text = "Elige tu objetivo principal", style = MaterialTheme.typography.titleMedium, color = TextBrown, fontWeight = FontWeight.Bold)
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SelectableGoalCard("BAJAR DE PESO", "Quiero reducir grasa corporal", Icons.Default.TrendingDown, viewModel.selectedObjective == "BAJAR DE PESO") { viewModel.toggleObjective("BAJAR DE PESO") }
                SelectableGoalCard("AUMENTAR MASA", "Quiero ganar músculo y fuerza", Icons.Default.TrendingUp, viewModel.selectedObjective == "AUMENTAR MASA MUSCULAR") { viewModel.toggleObjective("AUMENTAR MASA MUSCULAR") }
                SelectableGoalCard("SUBIR DE PESO", "Quiero recuperar peso saludable", Icons.Default.MonitorWeight, viewModel.selectedObjective == "SUBIR DE PESO") { viewModel.toggleObjective("SUBIR DE PESO") }
            }
            Text(text = "Tus medidas actuales", style = MaterialTheme.typography.titleMedium, color = TextBrown, fontWeight = FontWeight.Bold)
            Card(colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    DataInputRow("Edad", viewModel.age, { if (it.length <= 3) viewModel.age = it }, "años")
                    Divider(color = BackgroundBeige)
                    DataInputRow("Peso Actual", viewModel.currentWeight, { viewModel.currentWeight = it }, "kg")
                    Divider(color = BackgroundBeige)
                    DataInputRow("Peso Meta", viewModel.targetWeight, { viewModel.targetWeight = it }, "kg")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.saveAllData() }, colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange), shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth().height(56.dp), elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)) {
                Text("GUARDAR CAMBIOS", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun SelectableGoalCard(title: String, subtitle: String, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    val borderColor = if (isSelected) PrimaryOrange else Color.Transparent
    val containerColor = if (isSelected) PrimaryOrange.copy(alpha = 0.05f) else Color.White
    Card(colors = CardDefaults.cardColors(containerColor = containerColor), border = BorderStroke(2.dp, borderColor), elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 0.dp else 4.dp), shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth().clickable { onClick() }) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(BackgroundBeige), contentAlignment = Alignment.Center) { Icon(imageVector = icon, contentDescription = null, tint = PrimaryOrange) }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) { Text(text = title, fontWeight = FontWeight.Bold, color = TextBrown, fontSize = 16.sp); Text(text = subtitle, color = Color.Gray, fontSize = 12.sp) }
            Icon(imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.Circle, contentDescription = null, tint = if (isSelected) PrimaryOrange else Color.LightGray.copy(alpha = 0.5f), modifier = Modifier.size(24.dp))
        }
    }
}

@Composable
fun DataInputRow(label: String, value: String, onValueChange: (String) -> Unit, suffix: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = FontWeight.Medium, color = TextBrown, modifier = Modifier.width(100.dp))
        TextField(value = value, onValueChange = onValueChange, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent), placeholder = { Text("0") }, modifier = Modifier.weight(1f), textStyle = androidx.compose.ui.text.TextStyle(textAlign = TextAlign.End, fontWeight = FontWeight.Bold, color = TextBrown))
        Text(text = suffix, color = Color.Gray, modifier = Modifier.padding(start = 8.dp))
    }
}