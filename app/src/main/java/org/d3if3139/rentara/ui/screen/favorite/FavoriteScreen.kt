package org.d3if3139.rentara.ui.screen.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3139.rentara.navigation.Screen
import org.d3if3139.rentara.R
import org.d3if3139.rentara.ui.component.BottomNav
import org.d3if3139.rentara.ui.component.TopNav

@Composable
fun FavoriteScreen(navController: NavHostController, idUser: String? = null) {
    val id by remember { mutableStateOf(idUser!!) }
    Scaffold(
        topBar = { 
            TopNav(title = R.string.back_button, 
                navController = navController) 
        },
        bottomBar = {
            BottomNav(currentRoute = Screen.Favorite.route, navController = navController, id)
        }) 
    {
        ScreenContent(navController, modifier = Modifier.padding(it))
    }
}

@Composable
private fun ScreenContent(navController: NavHostController, modifier: Modifier) {
    Column(modifier = modifier) {

    }
}

@Preview
@Composable
private fun FavoriteScreenPrev() {
    FavoriteScreen(rememberNavController())
}