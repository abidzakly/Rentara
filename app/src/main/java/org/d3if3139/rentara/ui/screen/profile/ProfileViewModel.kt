package org.d3if3139.rentara.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3139.rentara.database.RentaraDao
import org.d3if3139.rentara.model.User
import java.util.Date

class ProfileViewModel(private val dao: RentaraDao): ViewModel() {

    suspend fun getCurrentUser(phoneNumber: String): User? {
        return dao.getCurrentUser(phoneNumber)
    }

    fun update(id: Long, nama: String, phoneNumber: String) {
        val user = User(
            id = id,
            nama = nama,
            phoneNumber = phoneNumber
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.updateUser(user)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteUserById(id)
        }
    }


}