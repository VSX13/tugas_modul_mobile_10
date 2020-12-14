package com.example.aplikasilistdaftarpesanan.pesanan

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pesanan_table")
data class Pesanan(
    var name: String,
    var description: String,
    var priority: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}