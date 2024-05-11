package org.d3if3139.rentara.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vlab2024.foodfusion.R
import org.d3if3139.rentara.ui.theme.GrayIcon
import org.d3if3139.rentara.ui.theme.GrayTextField
import org.d3if3139.rentara.ui.theme.LightGreen

@Composable
fun LoginScreen(navController: NavHostController) {
    Scaffold(containerColor = Color.White) {
        ScreenContent(modifier = Modifier.padding(it))
    }
}

@Composable
private fun ScreenContent(modifier: Modifier) {
    var phone by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(220.dp, 100.dp),
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = stringResource(R.string.app_logo)
        )
        Column {
            Text(text = stringResource(R.string.phone_number))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = phone,
                onValueChange = { phone = it },
                placeholder = { Text(text = "081326120387", color = GrayIcon) },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_phone_24),
                        contentDescription = stringResource(R.string.phone_logo),
                        tint = GrayIcon
                    )
                },
                colors = colors(unfocusedIndicatorColor = Color.Transparent, focusedIndicatorColor = Color.Transparent, unfocusedContainerColor = GrayTextField, focusedContainerColor = GrayTextField))
            Spacer(modifier = Modifier.padding(bottom = 24.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = { },
                shape = RoundedCornerShape(16.dp),
                colors = buttonColors(
                    LightGreen, Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.login_button),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Row {
            Text(text = stringResource(R.string.no_account), fontSize = 14.sp)
            Spacer(modifier = Modifier.padding(end = 4.dp))
            ClickableText(text = AnnotatedString(stringResource(R.string.sign_up_button)), onClick = {}, style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, color = LightGreen))
        }

    }
}

@Preview
@Composable
fun LoginScreenPrev() {
    LoginScreen(rememberNavController())
}