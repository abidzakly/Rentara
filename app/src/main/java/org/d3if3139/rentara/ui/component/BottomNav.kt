package org.d3if3139.rentara.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3139.rentara.navigation.Screen
import org.d3if3139.rentara.R
import org.d3if3139.rentara.ui.theme.DarkBlueDarker
import org.d3if3139.rentara.ui.theme.GrayIco

@Composable
fun BottomNav(currentRoute: String, navController: NavHostController, id: String) {
    BottomAppBar(
        modifier = Modifier
            .height(72.dp)
            .shadow(elevation = 5.dp, shape = RectangleShape),
        containerColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomNavButton(
                icon = R.drawable.baseline_home_filled_28,
                title = R.string.bottom_app_beranda,
                isSelected = currentRoute == Screen.Dashboard.route
            ) {
                navController.navigate(Screen.Dashboard.route) {
                   popUpTo(Screen.Dashboard.route)
                }
            }
            BottomNavButton(
                icon = R.drawable.baseline_favorite_28,
                title = R.string.bottom_app_favorit,
                isSelected = currentRoute == Screen.Favorite.route
            ) {
                navController.navigate(Screen.Favorite.route) {
                    popUpTo(Screen.Dashboard.route)
                }
            }
            BottomNavButton(
                icon = R.drawable.baseline_account_circle,
                title = R.string.bottom_app_profile,
                isSelected = currentRoute == Screen.Profile.route
            ) {
                navController.navigate(Screen.Profile.route) {
                    popUpTo(Screen.Login.route)
                }
            }
        }
    }
}

@Composable
fun BottomNavButton(icon: Int, title: Int, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .width(98.dp)
            .fillMaxHeight(),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = GrayIco,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = GrayIco
        ),
        enabled = !isSelected
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "Favorit",
                tint = if (isSelected) DarkBlueDarker else GrayIco
            )
            Text(
                text = stringResource(title),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
private fun BottomNavPrev() {
    BottomNav(currentRoute = Screen.Dashboard.route, navController = rememberNavController(), "")
}