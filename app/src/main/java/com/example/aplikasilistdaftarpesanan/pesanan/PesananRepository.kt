package com.example.aplikasilistdaftarpesanan.pesanan

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class PesananRepository(application: Application) {
    private var pesananDao: PesananDao
    private var allPesanan: LiveData<List<Pesanan>>
    init {
        val database: PesananDatabase = PesananDatabase.getInstance(
            application.applicationContext
        )!!
        pesananDao = database.pesananDao()
        allPesanan = pesananDao.getAllPesanan()
    }
    fun insert(pesanan: Pesanan) {
        val insertPesananAsyncTask = InsertPesananAsyncTask(pesananDao).execute(pesanan)
    }
    fun update(pesanan: Pesanan) {
        val updatePesananAsyncTask = UpdatePesananAsyncTask(pesananDao).execute(pesanan)
    }
    fun delete(pesanan: Pesanan) {
        val deletePesananAsyncTask = DeletePesananAsyncTask(pesananDao).execute(pesanan)
    }
    fun deleteAllPesanan() {
        val deleteAllPesananAsyncTask = DeleteAllPesananAsyncTask(
            pesananDao
        ).execute()
    }
    fun getAllPesanan(): LiveData<List<Pesanan>> {
        return allPesanan
    }
    companion object {
        private class InsertPesananAsyncTask(pesananDao: PesananDao) : AsyncTask<Pesanan, Unit, Unit>() {
            val pesananDao = pesananDao
            override fun doInBackground(vararg p0: Pesanan?) {
                pesananDao.insert(p0[0]!!)
            }
        }
        private class UpdatePesananAsyncTask(pesananDao: PesananDao) : AsyncTask<Pesanan, Unit, Unit>() {
            val pesananDao = pesananDao
            override fun doInBackground(vararg p0: Pesanan?) {
                pesananDao.update(p0[0]!!)
            }
        }
        private class DeletePesananAsyncTask(pesananDao: PesananDao) : AsyncTask<Pesanan, Unit, Unit>() {
            val pesananDao = pesananDao
            override fun doInBackground(vararg p0: Pesanan?) {
                pesananDao.delete(p0[0]!!)
            }
        }
        private class DeleteAllPesananAsyncTask(pesananDao: PesananDao) : AsyncTask<Unit, Unit, Unit>() {
            val pesananDao = pesananDao
            override fun doInBackground(vararg p0: Unit?) {
                pesananDao.deleteAllPesanan()
            }
        }
    }
}