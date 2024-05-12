package org.d3if3139.rentara.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3139.rentara.database.RentaraDao
import org.d3if3139.rentara.ui.screen.auth.AuthViewModel
import org.d3if3139.rentara.ui.screen.dashboard.DashboardViewModel
import org.d3if3139.rentara.ui.screen.favorite.FavoritesViewModel
import org.d3if3139.rentara.ui.screen.post.PostViewModel
import org.d3if3139.rentara.ui.screen.profile.ProfileViewModel

class ViewModelFactory(private val dao: RentaraDao, private val settingsDataStore: SettingsDataStore? = null): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(dao, settingsDataStore) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(dao) as T
        }else if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(dao) as T
        }else if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}