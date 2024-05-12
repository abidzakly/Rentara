package org.d3if3139.rentara.ui.screen.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3139.rentara.database.RentaraDao
import org.d3if3139.rentara.model.Recipe
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PostViewModel(private val dao: RentaraDao) : ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(title: String, description: String, serveTime: String, categories: String) {
        val recipe = Recipe(
            tanggal = formatter.format(Date()),
            judul = title,
            deskripsi = description,
            serveTime = serveTime,
            kategori = categories
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insertRecipe(recipe)
        }
    }

    suspend fun getDatabyId(id: Long): Recipe? {
        return dao.getRecipeById(id)
    }


    fun update(
        id: Long,
        title: String,
        description: String,
        serveTime: String,
        categories: String
    ) {
        val recipe = Recipe(
            id = id,
            tanggal = formatter.format(Date()),
            judul = title,
            deskripsi = description,
            serveTime = serveTime,
            kategori = categories
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.updateRecipe(recipe)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteRecipeById(id)
        }
    }

}