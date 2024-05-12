package org.d3if3139.rentara.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3139.rentara.database.RentaraDao
import org.d3if3139.rentara.model.User
import org.d3if3139.rentara.util.SettingsDataStore

class AuthViewModel(private val dao:RentaraDao, private val settingsDataStore: SettingsDataStore? = null) : ViewModel() {
    fun insert(nama: String, phoneNumber: String) {
        val user = User(
            nama= nama,
            phoneNumber = phoneNumber
        )

        viewModelScope.launch(Dispatchers.IO) {
                dao.insertUser(user)
        }
    }

    suspend fun isExist(phoneNumber: String): Boolean {
        return dao.isUserExist(phoneNumber) != null
    }

    suspend fun getCurrentUser(phoneNumber: String): User? {
        return dao.getCurrentUser(phoneNumber)
    }


}