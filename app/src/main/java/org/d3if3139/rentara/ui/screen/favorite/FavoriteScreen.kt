package org.d3if3139.rentara.ui.screen.favorite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import kotlinx.coroutines.launch
import org.d3if3139.rentara.navigation.Screen
import org.d3if3139.rentara.R
import org.d3if3139.rentara.database.RentaraDb
import org.d3if3139.rentara.model.Recipe
import org.d3if3139.rentara.ui.component.BottomNav
import org.d3if3139.rentara.ui.theme.RentaraPinkDarker
import org.d3if3139.rentara.util.SettingsDataStore
import org.d3if3139.rentara.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dataStore = SettingsDataStore(context)
    val showList by dataStore.layoutFlow.collectAsState(false)
    Scaffold(
        topBar = {
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
                        text = stringResource(R.string.bottom_app_favorit),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Black
                ),
                actions = {
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
                            tint = RentaraPinkDarker
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNav(currentRoute = Screen.Favorite.route, navController = navController)
        },
        containerColor = Color.White
    )
    {
        ScreenContent(showList, navController, modifier = Modifier.padding(it))
    }
}

@Composable
private fun ScreenContent(showList: Boolean, navController: NavHostController, modifier: Modifier) {
    val context = LocalContext.current
    val db = RentaraDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: FavoritesViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()
    var isFavorited by remember { mutableStateOf(false) }

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
            Text(text = "Anda belum memfavoritkan resep apapun.")
        }
    } else {
        if (showList) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(data) {
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
                items(data) {
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
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Card(
            onClick = { onClick() },
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
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
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
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
private fun FavoriteScreenPrev() {
    FavoriteScreen(rememberNavController())
}