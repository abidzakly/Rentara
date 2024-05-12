package org.d3if3139.rentara.ui.screen.dashboard

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
    val userViewModel: AuthViewModel = viewModel(factory = factory)
    val dashViewModel: DashboardViewModel = viewModel(factory = factory)

    var search by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    val searchResult by dashViewModel.search(search).collectAsState()


    LaunchedEffect(true) {
        phoneNumber = dataStore.idFlow.first()
        Log.d("MyComposable", "Phone Number: $phoneNumber")
        val data = userViewModel.getCurrentUser(phoneNumber) ?: return@LaunchedEffect
        nama = data.nama
        Log.d("MyComposable2", "Nama: $nama")
        phoneNumber = data.phoneNumber
        Log.d("MYCOMP7", "Recipe: $searchResult")
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
                                    if (showList) R.drawable.baseline_grid_view_24
                                    else R.drawable.baseline_view_list_24
                                ), contentDescription = stringResource(
                                    if (showList) R.string.grid_button
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
                            .fillMaxWidth(),
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
                        placeholder = {
                            Text(
                                text = "Mau cari resep apa?",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = GrayIcon
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        )
                    )
                }
            }
        },
        containerColor = Color.White,
        bottomBar = {
            BottomNav(currentRoute = Screen.Dashboard.route, navController = navController)
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
        ScreenContent(
            search,
            searchResult,
            showList,
            modifier = Modifier.padding(it),
            navController
        )
    }
}

@Composable
private fun ScreenContent(
    search: String,
    searchResult: List<Recipe>,
    showList: Boolean,
    modifier: Modifier,
    navController: NavHostController
) {
    val context = LocalContext.current
    val db = RentaraDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DashboardViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()
    var isFavorited by remember { mutableStateOf(false) }

    val isEmptySearch = search.isEmpty()
    val content = if (isEmptySearch) data else searchResult

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
        if (content.isNotEmpty()) {
            if (showList) {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 84.dp)
                ) {
                    items(content) {
                        isFavorited = it.isFavorite!!
                        MenuCard(
                            recipe = it,
                            isFavorited,
                            onFavChange = { isFavorited = !isFavorited },
                            onFavClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    viewModel.updateFavorites(isFavorited, it.id)
                                }
                            }
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
                    items(content) {
                        isFavorited = it.isFavorite!!
                        GridItem(
                            recipe = it,
                            isFavorited,
                            onFavChange = { isFavorited = !isFavorited },
                            onFavClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    viewModel.updateFavorites(isFavorited, it.id)
                                }
                            }
                        ) {
                            navController.navigate(Screen.EditPost.withId(it.id))
                        }
                    }
                }
            }
        } else {
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
                Text(text = "Data yang anda cari tidak ditemukan.")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuCard(
    recipe: Recipe,
    isFavorite: Boolean,
    onFavChange: (Boolean) -> Unit,
    onFavClick: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 4.dp),
        onClick = {}, shape = RoundedCornerShape(20.dp),
        elevation = cardElevation(
            defaultElevation = 10.dp
        ),
        colors = cardColors(containerColor = Color.Transparent),
    ) {
        Card(onClick = { onClick() }, colors = cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = recipe.judul,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp, color = Color.Black
                    )
                    IconButton(onClick = {
                        onFavChange(!isFavorite)
                        onFavClick()
                    }) {
                        Icon(
                            painter = painterResource(
                                id = if (isFavorite) R.drawable.baseline_favorite_28 else R.drawable.baseline_favorite_border_24
                            ),
                            contentDescription = if (isFavorite) "favorite" else "favorited"
                        )
                    }
                }
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
}

@Composable
fun GridItem(
    recipe: Recipe,
    isFavorite: Boolean,
    onFavChange: (Boolean) -> Unit,
    onFavClick: () -> Unit,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.shadow(elevation = 2.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            colors = cardColors(containerColor = Color.Transparent),
            border = BorderStroke(1.dp, Color.Gray)
        )
        {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = recipe.judul,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = {
                        onFavChange(!isFavorite)
                        onFavClick()
                    }) {
                        Icon(
                            painter = painterResource(
                                id = if (isFavorite) R.drawable.baseline_favorite_28 else R.drawable.baseline_favorite_border_24
                            ),
                            contentDescription = if (isFavorite) "favorite" else "favorited"
                        )
                    }
                }
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
}

@Preview
@Composable
private fun DashboardScreenPrev() {
    DashboardScreen(rememberNavController())
}
