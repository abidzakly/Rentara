package org.d3if3139.rentara.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val judul: String,
    val deskripsi: String,
    val kategori: String,
    val serveTime: String,
    val tanggal: String,
    val isFavorite: Boolean? = false
)
