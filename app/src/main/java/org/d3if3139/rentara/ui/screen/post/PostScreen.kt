package org.d3if3139.rentara.ui.screen.post

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.outlinedCardColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3139.rentara.R
import org.d3if3139.rentara.database.RentaraDb
import org.d3if3139.rentara.navigation.Screen
import org.d3if3139.rentara.ui.component.BottomNav
import org.d3if3139.rentara.ui.component.CustomTextField
import org.d3if3139.rentara.ui.component.DisplayAlertDialog
import org.d3if3139.rentara.ui.theme.GrayTextField
import org.d3if3139.rentara.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = RentaraDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: PostViewModel = viewModel(factory = factory)


    var judul by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var kategori by remember { mutableStateOf("") }
    var serveTime by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    val errorList = listOf(
        R.string.tambah_error_title,
        R.string.tambah_error_desc,
        R.string.tambah_error_servetime,
        R.string.tambah_error_kategori
    )

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getDatabyId(id) ?: return@LaunchedEffect
        judul = data.judul
        deskripsi = data.deskripsi
        serveTime = data.serveTime
        kategori = data.kategori
    }

    Scaffold(topBar = {
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
                    text = stringResource(R.string.tambah_resep),
                    fontSize = 22.sp
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = Color.Black
            ),
            actions = {
                IconButton(onClick = {
                    when {
                        judul == "" -> Toast.makeText(
                            context,
                            errorList[0],
                            Toast.LENGTH_SHORT
                        ).show()

                        deskripsi == "" -> Toast.makeText(
                            context,
                            errorList[2],
                            Toast.LENGTH_SHORT
                        ).show()

                        !Regex("[0-9]+").matches(serveTime) -> Toast.makeText(
                            context,
                            errorList[2],
                            Toast.LENGTH_SHORT
                        ).show()

                        kategori == "" -> Toast.makeText(
                            context,
                            errorList[3],
                            Toast.LENGTH_SHORT
                        ).show()

                        else -> {
                            if (id == null) {
                                viewModel.insert(judul, deskripsi, "$serveTime Menit", kategori)
                                navController.popBackStack()
                            } else {
                                viewModel.update(id, judul, deskripsi, "$serveTime Menit", kategori)
                                navController.popBackStack()
                            }
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = stringResource(
                            id = R.string.tombol_simpan
                        ), tint = MaterialTheme.colorScheme.primary
                    )
                }
                if (id != null) {
                    DeleteAction { showDialog = true }
                    DisplayAlertDialog(
                        openDialog = showDialog,
                        "Anda yakin ingin menghapus data ini?",
                        onDismissRequest = { showDialog = false }) {
                        showDialog = false
                        viewModel.delete(id)
                        navController.popBackStack()
                    }
                }
            }
        )
    },
        bottomBar = {
            BottomNav(currentRoute = Screen.Dashboard.route, navController = navController)
        },
        containerColor = Color.White) {
        ScreenContent(
            modifier = Modifier.padding(it),
            judul = judul,
            onTitleChange = { judul = it },
            deskripsi = deskripsi,
            onDescChange = { deskripsi = it },
            kategori = kategori,
            onCatChange = { kategori = it },
            serveTime = serveTime,
            onServeChange = { serveTime = it },
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    judul: String,
    onTitleChange: (String) -> Unit,
    deskripsi: String,
    onDescChange: (String) -> Unit,
    kategori: String,
    onCatChange: (String) -> Unit,
    serveTime: String,
    onServeChange: (String) -> Unit
) {

    val radioOptions =
        listOf(
            stringResource(id = R.string.kategori_resep_1),
            stringResource(id = R.string.kategori_resep_2),
            stringResource(id = R.string.kategori_resep_3),
            stringResource(id = R.string.kategori_resep_4)
        )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp, vertical = 24.dp),
    ) {
        Text(text = "Judul Resep")
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = judul,
            onValueChange = { onTitleChange(it) },
            placeholder = R.string.judul_resep
        )
        Text(text = "Deskripsi")
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            modifier = Modifier
                .height(132.dp),
            value = deskripsi,
            onValueChange = { onDescChange(it) },
            placeholder = R.string.deskripsi_resep
        )
        Text(text = "Waktu Saji")
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            isNumber = true,
            value = serveTime,
            onValueChange = { onServeChange(it) },
            placeholder = R.string.waktusaji_resep
        )
        Text(text = "Kategori")
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            colors = cardColors(
                containerColor = GrayTextField
            )
        ) {
            radioOptions.forEach { text ->
                GenderOption(
                    label = text,
                    isSelected = kategori == text,
                    modifier = Modifier
                        .selectable(
                            selected = kategori == text,
                            onClick = {
                                onCatChange(text)
                            },
                            role = Role.RadioButton
                        )
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(id = R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.hapus)) },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun GenderOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = isSelected, onClick = null, colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview
@Composable
private fun PostScreenPrev() {
    PostScreen(rememberNavController())
}