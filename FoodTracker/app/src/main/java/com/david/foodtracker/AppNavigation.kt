package com.david.foodtracker

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

enum class Screen {
    WELCOME, LOGIN, REGISTER, OBJECTIVES, RECIPES, RECIPE_LIST, RECIPE_DETAIL, TIPS, TIP_DETAIL, PLANS, ABOUT
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationControl() {
    var currentScreen by remember { mutableStateOf(Screen.WELCOME) }
    var selectedCategory by remember { mutableStateOf("") }
    var selectedRecipeId by remember { mutableIntStateOf(0) }
    var selectedTipId by remember { mutableIntStateOf(0) }

    var showExitDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    val activity = (LocalContext.current as? Activity)

    // Manejo del botón atrás físico
    BackHandler(enabled = true) {
        when (currentScreen) {
            Screen.RECIPE_DETAIL -> currentScreen = Screen.RECIPE_LIST
            Screen.RECIPE_LIST -> currentScreen = Screen.RECIPES
            Screen.TIP_DETAIL -> currentScreen = Screen.TIPS
            Screen.ABOUT -> currentScreen = Screen.WELCOME

            Screen.OBJECTIVES, Screen.RECIPES, Screen.TIPS, Screen.PLANS -> showExitDialog = true

            Screen.LOGIN, Screen.REGISTER -> currentScreen = Screen.WELCOME
            Screen.WELCOME -> showExitDialog = true
        }
    }

    // Diálogos
    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Salir de la aplicación") },
            text = { Text("¿Estás seguro que deseas salir?") },
            confirmButton = {
                Button(
                    onClick = {
                        showExitDialog = false
                        activity?.finish()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                ) { Text("Sí, salir") }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) { Text("Cancelar", color = TextBrown) }
            },
            containerColor = Color.White
        )
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Cerrar Sesión") },
            text = { Text("¿Quieres cerrar tu sesión actual y volver al inicio?") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        Firebase.auth.signOut()
                        currentScreen = Screen.WELCOME
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                ) { Text("Sí, cerrar sesión") }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) { Text("Cancelar", color = TextBrown) }
            },
            containerColor = Color.White
        )
    }

    val showMainControls = currentScreen in listOf(Screen.OBJECTIVES, Screen.RECIPES, Screen.TIPS, Screen.PLANS)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundBeige,
        contentWindowInsets = WindowInsets.navigationBars,
        // AGREGAMOS BOTÓN DE INFO EN LA BIENVENIDA
        floatingActionButton = {
            if (currentScreen == Screen.WELCOME) {
                FloatingActionButton(
                    onClick = { currentScreen = Screen.ABOUT },
                    containerColor = Color.White,
                    contentColor = PrimaryOrange
                ) {
                    Icon(Icons.Default.Info, contentDescription = "Créditos")
                }
            }
        },
        bottomBar = {
            if (showMainControls) {
                BottomNavigationBar(
                    onNavigate = { screen -> currentScreen = screen },
                    currentScreen = currentScreen
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {

            // LA PANTALLA ACTUAL
            when (currentScreen) {
                Screen.WELCOME -> WelcomeScreen(
                    onLoginClick = { currentScreen = Screen.LOGIN },
                    onRegisterClick = { currentScreen = Screen.REGISTER }
                )
                Screen.LOGIN -> LoginScreen(
                    onLoginSuccess = { currentScreen = Screen.OBJECTIVES },
                    onNavigateToRegister = { currentScreen = Screen.REGISTER }
                )
                Screen.REGISTER -> RegisterScreen(
                    onRegisterSuccess = { currentScreen = Screen.OBJECTIVES },
                    onNavigateToLogin = { currentScreen = Screen.LOGIN }
                )
                // Pasamos la lambda { showLogoutDialog = true } a las pantallas que la necesitan
                Screen.OBJECTIVES -> ObjectivesScreen(onLogout = { showLogoutDialog = true })

                Screen.RECIPES -> RecipesMenuScreen(
                    onCategorySelected = { category ->
                        selectedCategory = category
                        currentScreen = Screen.RECIPE_LIST
                    },
                    onLogout = { showLogoutDialog = true }
                )

                Screen.RECIPE_LIST -> RecipeListScreen(
                    category = selectedCategory,
                    onBack = { currentScreen = Screen.RECIPES },
                    onRecipeClick = { recipeId ->
                        selectedRecipeId = recipeId
                        currentScreen = Screen.RECIPE_DETAIL
                    }
                )
                Screen.RECIPE_DETAIL -> RecipeDetailScreen(
                    recipeId = selectedRecipeId,
                    onBack = { currentScreen = Screen.RECIPE_LIST }
                )

                Screen.TIPS -> TipsScreen(
                    onTipSelected = { tipId ->
                        selectedTipId = tipId
                        currentScreen = Screen.TIP_DETAIL
                    },
                    onLogout = { showLogoutDialog = true }
                )

                Screen.TIP_DETAIL -> TipDetailScreen(
                    tipId = selectedTipId,
                    onBack = { currentScreen = Screen.TIPS }
                )

                Screen.PLANS -> MealPlanScreen(onLogout = { showLogoutDialog = true })

                Screen.ABOUT -> AboutScreen(onBack = { currentScreen = Screen.WELCOME })
            }
        }
    }
}

@Composable
fun BottomNavigationBar(onNavigate: (Screen) -> Unit, currentScreen: Screen) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = PrimaryOrange
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Objetivos") },
            label = { Text("Objetivos") },
            selected = currentScreen == Screen.OBJECTIVES,
            onClick = { onNavigate(Screen.OBJECTIVES) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryOrange,
                unselectedIconColor = Color.Gray,
                indicatorColor = BackgroundBeige
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.DateRange, contentDescription = "Planes") },
            label = { Text("Planes") },
            selected = currentScreen == Screen.PLANS,
            onClick = { onNavigate(Screen.PLANS) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryOrange,
                unselectedIconColor = Color.Gray,
                indicatorColor = BackgroundBeige
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Menu, contentDescription = "Recetas") },
            label = { Text("Recetas") },
            selected = currentScreen == Screen.RECIPES || currentScreen == Screen.RECIPE_LIST || currentScreen == Screen.RECIPE_DETAIL,
            onClick = { onNavigate(Screen.RECIPES) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryOrange,
                unselectedIconColor = Color.Gray,
                indicatorColor = BackgroundBeige
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "Consejos") },
            label = { Text("Consejos") },
            selected = currentScreen == Screen.TIPS || currentScreen == Screen.TIP_DETAIL,
            onClick = { onNavigate(Screen.TIPS) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryOrange,
                unselectedIconColor = Color.Gray,
                indicatorColor = BackgroundBeige
            )
        )
    }
}