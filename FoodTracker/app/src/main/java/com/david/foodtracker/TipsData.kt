package com.david.foodtracker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.ui.graphics.vector.ImageVector

data class Tip(
    val id: Int,
    val title: String,
    val content: String,
    val icon: ImageVector
)

object TipsDataSource {
    val allTips = listOf(
        Tip(
            id = 1,
            title = "EJERCICIO",
            content = """
                La actividad física regular es fundamental para prevenir y manejar enfermedades cardíacas, accidentes cerebrovasculares, diabetes y varios tipos de cáncer.
                
                Recomendaciones:
                • Realiza al menos 150 minutos de actividad aeróbica moderada a la semana (como caminar rápido).
                • O bien, 75 minutos de actividad vigorosa (como correr).
                • Incluye ejercicios de fortalecimiento muscular 2 días a la semana.
                
                ¡Empieza poco a poco! Subir escaleras en lugar de usar el elevador ya cuenta.
            """.trimIndent(),
            icon = Icons.Default.Favorite
        ),
        Tip(
            id = 2,
            title = "ULTRAPROCESADOS",
            content = """
                Los alimentos ultraprocesados (como papitas, galletas empaquetadas, refrescos) suelen tener altas cantidades de azúcares, grasas saturadas y sodio.
                
                ¿Por qué evitarlos?
                • Son bajos en nutrientes esenciales.
                • Suelen contener aditivos y conservadores artificiales.
                • Generan picos de glucosa que aumentan el hambre rápidamente.
                
                Intenta sustituirlos por opciones naturales: fruta picada, nueces o palomitas de maíz caseras.
            """.trimIndent(),
            icon = Icons.Default.Warning
        ),
        Tip(
            id = 3,
            title = "TOMAR AGUA",
            content = """
                El agua es esencial para todas las funciones de tu cuerpo, incluyendo la digestión, absorción de nutrientes y regulación de la temperatura.
                
                Tips para hidratarte mejor:
                • No esperes a tener sed para beber agua.
                • Lleva siempre contigo una botella reutilizable.
                • Si no te gusta el agua simple, agrégale rodajas de limón, pepino o menta.
                • Bebe un vaso de agua antes de cada comida para mejorar la digestión y la saciedad.
            """.trimIndent(),
            icon = Icons.Default.WaterDrop
        ),
        Tip(
            id = 4,
            title = "CON QUÉ COCINAR",
            content = """
                La forma en que cocinas tus alimentos es tan importante como lo que comes.
                
                Mejores aceites para cocinar:
                • Aceite de Aguacate: Resiste altas temperaturas.
                • Aceite de Oliva Extra Virgen: Ideal para aderezar o cocinar a fuego bajo/medio.
                • Aceite de Coco: Úsalo con moderación por sus grasas saturadas.
                
                Evita: Aceites vegetales refinados (soya, maíz, canola) cuando sea posible, ya que pueden ser inflamatorios al calentarse mucho.
                
                Técnicas recomendadas: Al vapor, a la plancha, horneado o salteado. Evita los fritos.
            """.trimIndent(),
            icon = Icons.Default.Face // Usamos Face como placeholder de chef/cocina
        ),
        Tip(
            id = 5,
            title = "HORARIOS DE COMIDA",
            content = """
                Mantener horarios regulares ayuda a tu metabolismo a regularse y evita que llegues con demasiada hambre a la siguiente comida.
                
                Estrategia ideal:
                • Desayuno: Dentro de las primeras 2 horas al despertar.
                • Comidas: Cada 4 a 5 horas.
                • Cena: Al menos 2 horas antes de dormir para mejorar la calidad del sueño.
                
                Saltarse comidas suele llevar a comer en exceso más tarde. ¡La consistencia es clave!
            """.trimIndent(),
            icon = Icons.Default.Star // Usamos Star como reloj/horario importante
        )
    )

    fun getTipById(id: Int): Tip? {
        return allTips.find { it.id == id }
    }
}