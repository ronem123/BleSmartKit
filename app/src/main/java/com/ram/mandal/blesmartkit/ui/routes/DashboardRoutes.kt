package com.ram.mandal.blesmartkit.ui.routes


/**
 * Created by Ram Mandal on 23/01/2024
 * @System: Apple M1 Pro
 */

/**
 * Path constants
 */
object NavArgs {
    const val MENU_TYPE = "menu_type"
}

sealed class AppRoutes(val route: String) {

    // Static "entry" as the single startDestination for the graph.
    // Why: Compose Navigation expects a static start; we then navigate to the dynamic screen.
    object Entry : AppRoutes("entry") // <-- UPDATED: added entry route

    // Correct route pattern with a properly closed placeholder {menu_type}.
    // Why: This is the dynamic destination that takes an argument.
    object DetailScreen : AppRoutes(route = "detail/{${NavArgs.MENU_TYPE}}") {
        // Helper function to generate route with actual menuType
        fun withArgs(menuType: String): String =
            this.route.replace(oldValue = NavArgs.MENU_TYPE, newValue = menuType)

    }

    // Home route (optional, for fallback)
    object Home : AppRoutes(route = "/")
}


object NavigationRoutes {
    val home = "home"
    val dastoor = "dastoor"
    val fines = "fines"
    val center = "center"
    val links = "links"
}