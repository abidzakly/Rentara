package org.d3if3139.rentara.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3139.rentara.database.RentaraDao
import org.d3if3139.rentara.model.Recipe

class DashboardViewModel(dao: RentaraDao) : ViewModel() {
    val data: StateFlow<List<Recipe>> = dao.getAllRecipe().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}