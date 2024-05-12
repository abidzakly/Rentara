package org.d3if3139.rentara.ui.screen.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3139.rentara.navigation.Screen
import org.d3if3139.rentara.R
import org.d3if3139.rentara.database.RentaraDb
import org.d3if3139.rentara.ui.component.CustomTextField
import org.d3if3139.rentara.ui.component.TopNav
import org.d3if3139.rentara.ui.theme.RentaraPink
import org.d3if3139.rentara.util.ViewModelFactory

@Composable
fun RegisterScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopNav(title = R.string.signin, navController = navController)
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it), navController = navController)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val db = RentaraDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: AuthViewModel = viewModel(factory = factory)

    var fullname by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    var isExist by remember { mutableStateOf(true) }
    var isPressed by remember { mutableStateOf(false) }

    LaunchedEffect(isPressed) {
        isExist = viewModel.isExist(phoneNumber)
        Log.d("MyComposable4", "AccountStatus: $isExist")
    }
    Column(
        modifier = modifier
            .padding(horizontal = 40.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.rentara_logo),
            contentDescription = "Logo Rentara",
            modifier = Modifier.padding(bottom = 40.dp)
        )
        CustomTextField(
            value = fullname,
            onValueChange = { fullname = it },
            placeholder = R.string.fullname,
            false
        )
        CustomTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            placeholder = R.string.phone_number,
            isPhone = true
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = {
                isPressed = !isPressed
                if (!isExist) {
                    viewModel.insert(
                        fullname,
                        phoneNumber
                    )
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route)
                    }
                } else {
                    Toast.makeText(
                        context,
                        R.string.register_error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                RentaraPink, Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.register_button),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview
@Composable
private fun RegisterScreenPrev() {
    RegisterScreen(rememberNavController())
}