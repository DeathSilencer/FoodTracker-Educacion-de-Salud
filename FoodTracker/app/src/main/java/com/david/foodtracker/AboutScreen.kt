package com.david.foodtracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Créditos", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryOrange)
            )
        },
        containerColor = BackgroundBeige
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()), // Scroll por si hay muchas fotos
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.size(100.dp).clip(CircleShape).background(Color.White), contentAlignment = Alignment.Center) {
                Icon(imageVector = Icons.Default.School, contentDescription = null, tint = PrimaryOrange, modifier = Modifier.size(50.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Universidad Politécnica del Valle de México", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextBrown, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Ingeniería en Tecnologías de la Información", style = MaterialTheme.typography.bodyMedium, color = Color.Gray, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(32.dp))
            Divider(color = PrimaryOrange.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(24.dp))
            Text("Desarrollado por:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextBrown)
            Spacer(modifier = Modifier.height(24.dp))

            // Integrantes con Foto (Recuerda poner las fotos en drawable)
            // Si no tienes foto, usa R.drawable.ic_launcher_background como placeholder
            TeamMember("Estefani Jazmín Aguillón Martínez", R.drawable.dev_jazmin) // Cambiar por R.drawable.dev_estefani
            TeamMember("Mario Arturo Martínez Ángeles", R.drawable.dev_mario)     // Cambiar por R.drawable.dev_mario
            TeamMember("Liliana Luna Vargas", R.drawable.dev_liliana)               // Cambiar por R.drawable.dev_liliana
            TeamMember("Brian Ezequiel Sanchez", R.drawable.dev_brian)            // Cambiar por R.drawable.dev_brian

            Spacer(modifier = Modifier.height(40.dp))
            Text("Versión 1.0", fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun TeamMember(name: String, photoRes: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        // Foto Circular
        Image(
            painter = painterResource(id = photoRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TextBrown
        )
    }
}