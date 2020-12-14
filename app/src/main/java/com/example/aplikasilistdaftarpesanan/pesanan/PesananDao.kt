package com.example.aplikasilistdaftarpesanan.pesanan

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PesananDao {
    @Insert
    fun insert(pesanan: Pesanan)
    @Update
    fun update(pesanan: Pesanan)
    @Delete
    fun delete(pesanan: Pesanan)
    @Query("DELETE FROM pesanan_table")
    fun deleteAllPesanan()
    @Query("SELECT * FROM pesanan_table ORDER BY priority DESC")
    fun getAllPesanan(): LiveData<List<Pesanan>>
}