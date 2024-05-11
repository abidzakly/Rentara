package org.d3if3139.mobpro1.navigation


const val KEY_ID_DATA = "idData"

sealed class Screen(val route: String) {
    data object Dashboard: Screen("mainScreen")
    data object Login: Screen("loginScreen")
    data object Register: Screen("registerScreen")
}