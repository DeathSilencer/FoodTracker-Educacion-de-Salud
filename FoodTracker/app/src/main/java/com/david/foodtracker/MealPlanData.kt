package com.david.foodtracker

data class DailyMenu(
    val day: String,       // "Lunes"
    val breakfast: String, // "Avena con manzana"
    val lunch: String,     // "Pollo a la plancha..."
    val dinner: String,    // "Ensalada..."
    val snack: String      // "Almendras"
)

object MealPlanDataSource {

    // --- PLAN 1: BAJAR DE PESO (Déficit calórico, alto en fibra) ---
    val planWeightLoss = listOf(
        DailyMenu("Lunes", "Claras con espinaca", "Pechuga asada y ensalada", "Atún con pico de gallo", "1 Manzana"),
        DailyMenu("Martes", "Avena con agua y canela", "Pescado empapelado", "Nopales asados con queso", "Gelatina light"),
        DailyMenu("Miércoles", "Pan integral con aguacate", "Caldo de pollo con verduras", "Ensalada de atún", "Pepinos con limón"),
        DailyMenu("Jueves", "Licuado de frutos rojos", "Tostadas de pollo hervido", "Huevo a la mexicana", "1 Pera"),
        DailyMenu("Viernes", "Yogurt griego sin azúcar", "Fajitas de pollo con pimientos", "Ceviche de pescado", "Nueces"),
        DailyMenu("Sábado", "Omelette de champiñones", "Bistec asado con nopales", "Tacos de lechuga", "Jícama"),
        DailyMenu("Domingo", "Hot cakes de avena", "Sopa de verduras y pollo", "Quesadillas sin grasa", "Té verde")
    )

    // --- PLAN 2: AUMENTAR MASA (Alto en proteína y carbohidratos complejos) ---
    val planMuscleGain = listOf(
        DailyMenu("Lunes", "3 Huevos enteros y pan", "Pollo, arroz y frijoles", "Sandwich de pavo doble", "Batido de proteína"),
        DailyMenu("Martes", "Avena con leche y plátano", "Carne molida con pasta", "Atún y papa cocida", "Yogurt con granola"),
        DailyMenu("Miércoles", "Hot cakes con crema de maní", "Bistec, arroz y lentejas", "Pollo deshebrado y tostadas", "Frutos secos"),
        DailyMenu("Jueves", "Huevos revueltos con jamón", "Pechuga empanizada (aire)", "Burritos de res", "Barra de proteína"),
        DailyMenu("Viernes", "Licuado de plátano y avena", "Albóndigas con arroz", "Tacos de bistec", "Queso cottage"),
        DailyMenu("Sábado", "Chilaquiles con pollo y huevo", "Pasta a la boloñesa", "Hamburguesa casera", "Licuado de chocolate"),
        DailyMenu("Domingo", "Machaca con huevo", "Carne asada y guacamole", "Molletes con pico de gallo", "Leche con chocolate")
    )

    // --- PLAN 3: GENERAL / MANTENIMIENTO (Balanceado) ---
    val planBalanced = listOf(
        DailyMenu("Lunes", "Huevo con ejotes", "Pollo con verduras", "Sincronizada", "Fruta"),
        DailyMenu("Martes", "Cereal integral", "Picadillo de res", "Pan tostado con queso", "Yogurt"),
        DailyMenu("Miércoles", "Molletes integrales", "Pescado al ajillo", "Ensalada mixta", "Galletas Marías"),
        DailyMenu("Jueves", "Fruta con queso cottage", "Tacos dorados (horno)", "Huevo duro", "Palomitas"),
        DailyMenu("Viernes", "Licuado verde", "Pollo en salsa verde", "Cena ligera", "Gelatina"),
        DailyMenu("Sábado", "Libre (con medida)", "Comida fuera (moderada)", "Cena ligera", "Postre pequeño"),
        DailyMenu("Domingo", "Barbacoa (sin grasa)", "Consomé de pollo", "Cereal con leche", "Fruta picada")
    )

    // Función inteligente para devolver el plan correcto
    fun getPlanForObjective(objective: String?): List<DailyMenu> {
        return when (objective) {
            "BAJAR DE PESO" -> planWeightLoss
            "AUMENTAR MASA MUSCULAR" -> planMuscleGain
            "SUBIR DE PESO" -> planMuscleGain // Usamos el mismo de masa
            else -> planBalanced // Si no ha elegido nada, le damos el balanceado
        }
    }
}