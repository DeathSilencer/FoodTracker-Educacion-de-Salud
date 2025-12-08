package com.david.foodtracker

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

// Modelo de datos completo
data class Recipe(
    val id: Int, // Identificador único para encontrar la receta
    val title: String,
    val description: String,
    val imageRes: Int,
    val ingredients: List<String>,
    val instructions: List<String>
)

// Base de datos local de recetas
object RecipeDataSource {
    val allRecipes = listOf(
        // --- POLLO ---
        Recipe(
            id = 1,
            title = "Pollo a la Plancha",
            description = "Pechuga de pollo marinada con limón y hierbas.",
            imageRes = R.drawable.pollo_plancha, // Recuerda cambiar por tu imagen real
            ingredients = listOf("2 pechugas de pollo", "2 limones", "Sal y pimienta", "Orégano", "Aceite de oliva"),
            instructions = listOf("1. Lavar el pollo.", "2. Marinar con el jugo de limón y especias por 30 min.", "3. Calentar el sartén con poco aceite.", "4. Cocinar 5 minutos por lado hasta dorar.")
        ),
        Recipe(
            id = 2,
            title = "Fajitas de Pollo",
            description = "Tiras de pollo salteadas con pimientos.",
            imageRes = R.drawable.fajitas_pollo,
            ingredients = listOf("500g fajitas de pollo", "1 pimiento rojo", "1 pimiento verde", "1 cebolla", "Tortillas"),
            instructions = listOf("1. Cortar verduras en tiras.", "2. Sofreír el pollo hasta que esté blanco.", "3. Agregar verduras y cocinar 10 min.", "4. Servir con tortillas calientes.")
        ),
        Recipe(
            id = 3,
            title = "Caldo de Pollo",
            description = "Reconfortante caldo con verduras frescas.",
            imageRes = R.drawable.caldo_pollo,
            ingredients = listOf("1 pechuga con hueso", "2 zanahorias", "1 calabaza", "Garbanzos", "Cilantro"),
            instructions = listOf("1. Hervir el pollo con agua y sal.", "2. Retirar espuma.", "3. Agregar zanahoria picada.", "4. Al final agregar calabaza y cilantro.")
        ),

        // --- PESCADO ---
        Recipe(
            id = 4,
            title = "Ceviche de Pescado",
            description = "Fresco y ácido, ideal para el calor.",
            imageRes = R.drawable.ceviche_pescado,
            ingredients = listOf("500g filete de pescado", "10 limones", "Pepino", "Cebolla morada", "Tostadas"),
            instructions = listOf("1. Picar el pescado en cubos.", "2. Cocer en jugo de limón por 4 horas.", "3. Mezclar con verdura picada.", "4. Servir en tostadas.")
        ),
        Recipe(
            id = 5,
            title = "Pescado Empapelado",
            description = "Cocinado al vapor en su propio jugo.",
            imageRes = R.drawable.pescado_empapelado,
            ingredients = listOf("1 filete de pescado", "Papel aluminio", "Jitomate", "Cebolla", "Epazote"),
            instructions = listOf("1. Colocar pescado sobre aluminio.", "2. Agregar verduras encima.", "3. Cerrar muy bien el paquete.", "4. Cocinar en comal 10 minutos.")
        ),

        // --- RES ---
        Recipe(
            id = 6,
            title = "Bistec a la Mexicana",
            description = "Clásico guisado casero.",
            imageRes = R.drawable.bistec_mexicana,
            ingredients = listOf("500g bistec de res", "3 jitomates", "1/2 cebolla", "1 chile serrano"),
            instructions = listOf("1. Cortar bistec en tiras y freír.", "2. Licuar jitomate o picarlo.", "3. Agregar salsa a la carne.", "4. Sazonar y dejar hervir.")
        ),
        Recipe(
            id = 7,
            title = "Albóndigas",
            description = "Bolitas de carne en salsa roja.",
            imageRes = R.drawable.albondigas,
            ingredients = listOf("Carne molida", "Huevo", "Arroz cocido", "Salsa de chipotle"),
            instructions = listOf("1. Mezclar carne con huevo y arroz.", "2. Formar bolitas.", "3. Hervir la salsa.", "4. Echar las bolitas y cocinar 20 min.")
        ),

        // --- VEGETALES ---
        Recipe(
            id = 8,
            title = "Ensalada César",
            description = "Ligera y crujiente.",
            imageRes = R.drawable.ensalada_cesar,
            ingredients = listOf("Lechuga orejona", "Aderezo césar", "Queso parmesano", "Crutones"),
            instructions = listOf("1. Lavar y desinfectar lechuga.", "2. Trocear con las manos.", "3. Mezclar con aderezo.", "4. Decorar con queso y pan.")
        )
    )

    // Función para obtener recetas por categoría (Lógica simple basada en IDs o Palabras clave)
    // Para simplificar, asignaremos manualmente en esta función demo
    fun getRecipesByCategory(category: String): List<Recipe> {
        return when(category) {
            "POLLO" -> allRecipes.filter { it.id in 1..3 }
            "PESCADO" -> allRecipes.filter { it.id in 4..5 }
            "RES" -> allRecipes.filter { it.id in 6..7 }
            "VEGETALES" -> allRecipes.filter { it.id == 8 }
            else -> emptyList()
        }
    }

    // Función para obtener UNA receta por su ID
    fun getRecipeById(id: Int): Recipe? {
        return allRecipes.find { it.id == id }
    }
}