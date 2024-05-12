package org.d3if3139.rentara.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.d3if3139.rentara.ui.screen.dashboard.DashboardScreen
import org.d3if3139.rentara.ui.screen.favorite.FavoriteScreen
import org.d3if3139.rentara.ui.screen.auth.LoginScreen
import org.d3if3139.rentara.ui.screen.post.PostScreen
import org.d3if3139.rentara.ui.screen.auth.RegisterScreen
import org.d3if3139.rentara.ui.screen.profile.ProfileScreen
import org.d3if3139.rentara.util.SettingsDataStore
import org.d3if3139.rentara.util.dataStore

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current

    val dataStore = SettingsDataStore(context)
    var loginStatus by remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        if (dataStore.loginFlow.first()) {
            loginStatus = !loginStatus
        } else {
            loginStatus = false
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (loginStatus) Screen.Dashboard.route else Screen.Login.route
    ) {
        composable(
            route = Screen.Dashboard.route
        ) {
            DashboardScreen(navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(
            route = Screen.Favorite.route
        ) {
            FavoriteScreen(navController)
        }
        composable(route = Screen.NewPost.route) {
            PostScreen(navController)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController)
        }
        composable(
            route = Screen.EditPost.route,
            arguments = listOf(
                navArgument(KEY_ID_DATA) {
                    type = NavType.LongType
                }
            )
        ) {
            val id = it.arguments?.getLong(KEY_ID_DATA)
            PostScreen(navController, id)
        }
    }
}