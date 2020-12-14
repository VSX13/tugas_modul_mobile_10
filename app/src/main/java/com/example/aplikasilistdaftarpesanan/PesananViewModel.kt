package com.example.aplikasilistdaftarpesanan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.aplikasilistdaftarpesanan.pesanan.Pesanan
import com.example.aplikasilistdaftarpesanan.pesanan.PesananRepository

class PesananViewModel(application: Application) : AndroidViewModel(application)
{
    private var repository: PesananRepository =
        PesananRepository(application)
    private var allPesanan: LiveData<List<Pesanan>> = repository.getAllPesanan()
    fun insert(pesanan: Pesanan) {
        repository.insert(pesanan)
    }
    fun update(pesanan: Pesanan) {
        repository.update(pesanan)
    }
    fun delete(pesanan: Pesanan) {
        repository.delete(pesanan)
    }
    fun deleteAllPesanan() {
        repository.deleteAllPesanan()
    }
    fun getAllPesanan(): LiveData<List<Pesanan>> {
        return allPesanan
    }
}
