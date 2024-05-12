package org.d3if3139.rentara.ui.screen.post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun PostScreen(navController: NavHostController, id: Long? = null) {
    Scaffold {
        ScreenContent(modifier = Modifier.padding(it), navController = navController)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    Column {
        
    }
}

@Preview
@Composable
private fun PostScreenPrev() {
    PostScreen(rememberNavController())
}