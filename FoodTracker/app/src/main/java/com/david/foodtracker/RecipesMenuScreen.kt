package com.david.foodtracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Water
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CategoryItem(val name: String, val icon: ImageVector, val id: String)

@Composable
fun RecipesMenuScreen(
    onCategorySelected: (String) -> Unit,
    onLogout: () -> Unit // <--- ¡ESTE ES EL QUE FALTABA!
) {
    val categories = listOf(
        CategoryItem("PESCADO", Icons.Default.Water, "PESCADO"),
        CategoryItem("POLLO", Icons.Default.Egg, "POLLO"),
        CategoryItem("RES", Icons.Default.Restaurant, "RES"),
        CategoryItem("VEGETALES", Icons.Default.Eco, "VEGETALES")
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Pasamos onLogout al HeaderTitle
        HeaderTitle(text = "RECETAS NUTRITIVAS", showLogout = true, onLogout = onLogout)

        Text(
            text = "¿Qué se te antoja cocinar hoy?",
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(categories) { item ->
                CategoryCard(item, onClick = { onCategorySelected(item.id) })
            }
        }
    }
}

@Composable
fun CategoryCard(item: CategoryItem, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth().aspectRatio(1f).clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.size(70.dp).clip(CircleShape).background(PrimaryOrange.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                Icon(imageVector = item.icon, contentDescription = null, tint = PrimaryOrange, modifier = Modifier.size(36.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextBrown)
        }
    }
}