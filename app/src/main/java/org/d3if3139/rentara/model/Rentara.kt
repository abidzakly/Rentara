package org.d3if3139.rentara.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rentara")
data class Rentara(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val judul: String,
    val deskripsi: String,
    val instruksi: String,
    val gambar: Int,
    val tanggal: String
)
