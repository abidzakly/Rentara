package org.d3if3139.rentara.ui.screen.profile

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.d3if3139.rentara.R
import org.d3if3139.rentara.database.RentaraDb
import org.d3if3139.rentara.navigation.Screen
import org.d3if3139.rentara.ui.component.BottomNav
import org.d3if3139.rentara.ui.component.CustomTextField
import org.d3if3139.rentara.ui.component.DisplayAlertDialog
import org.d3if3139.rentara.ui.component.TopNav
import org.d3if3139.rentara.ui.theme.RentaraPink
import org.d3if3139.rentara.ui.theme.RentaraPinkDarker
import org.d3if3139.rentara.ui.theme.RentaraYellow
import org.d3if3139.rentara.util.SettingsDataStore
import org.d3if3139.rentara.util.ViewModelFactory

@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopNav(title = R.string.bottom_app_profile, navController = navController)
        },
        bottomBar = {
            BottomNav(currentRoute = Screen.Profile.route, navController = navController)
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it), navController)
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    var id by remember { mutableLongStateOf(-1L) }
    var fullname by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var deleteDialog by remember { mutableStateOf(false) }
    var logoutDialog by remember { mutableStateOf(false) }
    var ubahDialog by remember { mutableStateOf(false) }


    val context = LocalContext.current

    val db = RentaraDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: ProfileViewModel = viewModel(factory = factory)

    val dataStore = SettingsDataStore(context)
    val isLoggedIn by dataStore.loginFlow.collectAsState(true)

    LaunchedEffect(true) {
        val data = viewModel.getCurrentUser(dataStore.idFlow.first())
        if (data != null) {
            id = data.id
            fullname = data.nama
            phoneNumber = data.phoneNumber
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Kelola Profil")
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.profile_big),
            contentDescription = "Profile Picture"
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            value = fullname,
            onValueChange = { fullname = it },
            placeholder = R.string.fullname
        )
        CustomTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            placeholder = R.string.phone_number,
            isPhone = true
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                modifier = Modifier
                    .height(56.dp),
                onClick = {
                    if (id != null) {
                        ubahDialog = true
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    RentaraYellow, Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.profile_edit_button),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                modifier = Modifier
                    .height(56.dp),
                onClick = {
                    if (id != null) {
                        deleteDialog = true
                    }

                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    RentaraPinkDarker, Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.delete_account),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier
                    .height(56.dp),
                onClick = {
                    logoutDialog = true
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    RentaraPink, Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.logout_button),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            if (deleteDialog) {
                DisplayAlertDialog(
                    openDialog = deleteDialog,
                    text = "Anda yakin ingin Menghapus Akun?",
                    onDismissRequest = { deleteDialog = false }) {
                    Toast.makeText(
                        context,
                        "Akun telah terhapus.",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.delete(id)
                    navController.popBackStack(Screen.Login.route, inclusive = true)
                    navController.navigate(Screen.Login.route)
                    deleteDialog = false
                }
            }
            if (logoutDialog) {
                DisplayAlertDialog(
                    openDialog = logoutDialog,
                    text = "Anda yakin ingin keluar?",
                    onDismissRequest = { logoutDialog = false }) {
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStore.setLoginStatus(!isLoggedIn)
                        dataStore.setUserId("")
                    }
                    Toast.makeText(
                        context,
                        "Berhasil keluar.",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.popBackStack(Screen.Login.route, inclusive = true)
                    navController.navigate(Screen.Login.route)
                    logoutDialog = false
                }
            }
            if (ubahDialog) {
                DisplayAlertDialog(
                    openDialog = ubahDialog,
                    text = "Anda yakin ingin mengubah data?",
                    onDismissRequest = { ubahDialog = false }) {
                    viewModel.update(id, fullname, phoneNumber)
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStore.setUserId(phoneNumber)
                    }
                    Toast.makeText(
                        context,
                        "Sukses",
                        Toast.LENGTH_SHORT
                    ).show()
                    ubahDialog = false
                }
            }
        }
    }
}

@Composable
fun DeleteAlert(showDialog: Boolean) {

}

@Preview
@Composable
private fun ProfileScreenPrev() {
    ProfileScreen(rememberNavController())
}