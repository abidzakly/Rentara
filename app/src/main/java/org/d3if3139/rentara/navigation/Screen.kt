package org.d3if3139.rentara.navigation


const val KEY_ID_DATA = "idData"

sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboardScreen/{$KEY_ID_DATA}") {
        fun withId(id: String) = "dashboardScreen/$id"
    }
    data object Login : Screen("loginScreen")
    data object Register : Screen("registerScreen")
    data object Favorite : Screen("favoriteScreen/{$KEY_ID_DATA}") {
        fun withId(id: String) = "favoriteScreen/$id"
    }
    data object NewPost : Screen("postScreen")
    data object Profile : Screen("profileScreen")
    data object EditPost : Screen("postScreen/{$KEY_ID_DATA}") {
        fun withId(id: Long) = "postScreen/$id"
    }
}