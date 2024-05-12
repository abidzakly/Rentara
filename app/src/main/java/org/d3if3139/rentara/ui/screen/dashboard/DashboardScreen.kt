package org.d3if3139.rentara.ui.screen.dashboard

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.colors
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
import org.d3if3139.rentara.navigation.Screen
import org.d3if3139.rentara.R
import org.d3if3139.rentara.database.RentaraDb
import org.d3if3139.rentara.model.Recipe
import org.d3if3139.rentara.ui.component.BottomNav
import org.d3if3139.rentara.ui.screen.auth.AuthViewModel
import org.d3if3139.rentara.ui.theme.GrayIcon
import org.d3if3139.rentara.ui.theme.GrayTextField
import org.d3if3139.rentara.ui.theme.RentaraPink
import org.d3if3139.rentara.ui.theme.RentaraPinkDarker
import org.d3if3139.rentara.ui.theme.RentaraYellow
import org.d3if3139.rentara.util.SettingsDataStore
import org.d3if3139.rentara.util.ViewModelFactory

@Composable
fun DashboardScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dataStore = SettingsDataStore(context)
    val showList by dataStore.layoutFlow.collectAsState(false)

    val db = RentaraDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: AuthViewModel = viewModel(factory = factory)

    var search by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        phoneNumber = dataStore.idFlow.first()
        Log.d("MyComposable", "Phone Number: $phoneNumber")
        val data = viewModel.getCurrentUser(phoneNumber) ?: return@LaunchedEffect
        nama = data.nama
        Log.d("MyComposable2", "Nama: $nama")
        phoneNumber = data.phoneNumber
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                RentaraPink, RentaraPinkDarker
                            )
                        ), shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    )
                    .height(195.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Column(modifier = Modifier.padding(30.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Hello, $nama",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = GrayTextField
                        )
                        IconButton(onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                dataStore.saveLayout(!showList)
                            }
                        }) {
                            Icon(
                                painter = painterResource(
                                    if (showList as Boolean) R.drawable.baseline_grid_view_24
                                    else R.drawable.baseline_view_list_24
                                ), contentDescription = stringResource(
                                    if (showList as Boolean) R.string.grid_button
                                    else R.string.list_button
                                ),
                                tint = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(bottom = 15.dp))
                    TextField(
                        value = search,
                        onValueChange = { search = it },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp),
                        colors = colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_search_24),
                                contentDescription = stringResource(
                                    R.string.search_icon
                                ),
                                tint = GrayIcon
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.search_placeholder),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = GrayIcon
                            )
                        }
                    )
                }
            }
        },
        containerColor = Color.White,
        bottomBar = {
            BottomNav(currentRoute = Screen.Dashboard.route, navController = navController, phoneNumber)
        },
        floatingActionButton = {
            FloatingActionButton(containerColor = RentaraYellow, onClick = {
                navController.navigate(Screen.NewPost.route)
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.post_button),
                    tint = Color.Black
                )
            }
        }
    ) {
        ScreenContent(showList, modifier = Modifier.padding(it), navController)
    }
}

@Composable
private fun ScreenContent(showList: Boolean, modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val db = RentaraDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DashboardViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()
    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.list_empty_illustration),
                contentDescription = "List Empty Image"
            )
            Text(text = stringResource(id = R.string.list_kosong))
        }
    } else {
        if (showList) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(data) {
                    MenuCard(
                        recipe = it
                    ) {
                        navController.navigate(Screen.EditPost.withId(it.id))
                    }
                }
            }
        } else {
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 84.dp)
            ) {
                items(data) {
                    MenuCard(
                        recipe = it
                    ) {
                        navController.navigate(Screen.EditPost.withId(it.id))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuCard(recipe: Recipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        onClick = {}, shape = RoundedCornerShape(20.dp),
        elevation = cardElevation(
            defaultElevation = 10.dp
        ),
        colors = cardColors(containerColor = Color.Transparent),
    ) {
        Card(onClick = { onClick() }, colors = cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = recipe.judul,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp, color = Color.Black
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = recipe.kategori,
                        fontSize = 12.sp,
                    )
                    Text(
                        text = recipe.serveTime,
                        fontSize = 12.sp
                    )
                }
            }
        }

    }
    Spacer(modifier = Modifier.padding(bottom = 24.dp))
}

@Composable
fun GridItem(recipe: Recipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color.Gray)
    )
    {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = recipe.judul,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = recipe.kategori,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = recipe.serveTime
            )

        }
    }
}

@Preview
@Composable
private fun DashboardScreenPrev() {
    DashboardScreen(rememberNavController())
}