package org.d3if3139.rentara.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3139.rentara.database.RentaraDao
import org.d3if3139.rentara.model.Rentara

class DashboardViewModel(dao: RentaraDao) : ViewModel() {
    val data: StateFlow<List<Rentara>> = dao.getData().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}