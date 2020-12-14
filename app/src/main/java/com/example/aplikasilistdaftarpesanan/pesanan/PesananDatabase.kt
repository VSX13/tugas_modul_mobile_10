package com.example.aplikasilistdaftarpesanan.pesanan

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Pesanan::class], version = 1)
abstract class PesananDatabase : RoomDatabase() {
    abstract fun pesananDao(): PesananDao
    companion object {
        private var instance: PesananDatabase? = null
        fun getInstance(context: Context): PesananDatabase? {
            if (instance == null) {
                synchronized(PesananDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PesananDatabase::class.java, "pesanan_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }
        fun destroyInstance() {
            instance = null
        }
        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }
    class PopulateDbAsyncTask(db: PesananDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val pesananDao = db?.pesananDao()
        override fun doInBackground(vararg p0: Unit?) {
            pesananDao?.insert(Pesanan("Budi", "Nasi Goreng dan Es Jeruk", 1))
        }
    }
}