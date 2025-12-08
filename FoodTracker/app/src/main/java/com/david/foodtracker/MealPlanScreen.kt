package com.david.foodtracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MealPlanScreen(
    onLogout: () -> Unit, // <--- ¡ESTE ES EL PARÁMETRO QUE FALTABA!
    viewModel: MealPlanViewModel = viewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderTitle(text = "PLAN: ${viewModel.userObjectiveName}", showLogout = true, onLogout = onLogout)

        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxSize()) {
            items(viewModel.currentPlan) { dailyMenu -> DayPlanCard(dailyMenu) }
        }
    }
}

@Composable
fun DayPlanCard(menu: DailyMenu) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 3.dp), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 12.dp)) {
                Box(modifier = Modifier.background(PrimaryOrange, RoundedCornerShape(8.dp)).padding(horizontal = 12.dp, vertical = 6.dp)) {
                    Text(text = menu.day, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            MealRow(icon = Icons.Default.WbSunny, title = "Desayuno", content = menu.breakfast)
            Divider(color = BackgroundBeige, modifier = Modifier.padding(vertical = 8.dp))
            MealRow(icon = Icons.Default.Restaurant, title = "Comida", content = menu.lunch)
            Divider(color = BackgroundBeige, modifier = Modifier.padding(vertical = 8.dp))
            MealRow(icon = Icons.Default.RestaurantMenu, title = "Cena", content = menu.dinner)
            Divider(color = BackgroundBeige, modifier = Modifier.padding(vertical = 8.dp))
            MealRow(icon = Icons.Default.Restaurant, title = "Snack", content = menu.snack, isSmall = true)
        }
    }
}

@Composable
fun MealRow(icon: ImageVector, title: String, content: String, isSmall: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null, tint = if (isSmall) Color.Gray else PrimaryOrange, modifier = Modifier.size(if (isSmall) 18.dp else 24.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = title, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Text(text = content, fontSize = 16.sp, color = TextBrown)
        }
    }
}