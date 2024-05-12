package org.d3if3139.rentara.ui.component

import android.widget.Toast
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3139.rentara.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNav(title: Int, navController: NavHostController) {

    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(
                        id = R.string.back_button
                    ),
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        title = {
            Text(
                text = stringResource(title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.Black
        )
    )
}

@Preview
@Composable
private fun TopNavPrev() {
    TopNav(R.string.signin, rememberNavController())
}