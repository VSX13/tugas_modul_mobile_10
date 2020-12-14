package com.example.aplikasilistdaftarpesanan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_pesanan.*

class AddEditPesananActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "com.example.aplikasilistdaftarpesanan.pesanan.EXTRA_ID"
        const val EXTRA_NAME = "com.example.aplikasilistdaftarpesanan.pesanan.EXTRA_JUDUL"
        const val EXTRA_DESKRIPSI = "com.example.aplikasilistdaftarpesanan.pesanan.EXTRA_DESKRIPSI"
        const val EXTRA_PRIORITAS = "com.example.aplikasilistdaftarpesanan.pesanan.EXTRA_PRIORITAS"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pesanan)
        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 5
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Pesanan"
            edit_text_name.setText(intent.getStringExtra(EXTRA_NAME))
            edit_text_description.setText(intent.getStringExtra(EXTRA_DESKRIPSI))
            number_picker_priority.value = intent.getIntExtra(EXTRA_PRIORITAS, 1)
        } else {
            title = "Tambah Pesanan"
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_pesanan_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.save_pesanan -> {
                savePesanan()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun savePesanan() {
        if (edit_text_name.text.toString().trim().isBlank() || edit_text_description.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Pesanan kosong!", Toast.LENGTH_SHORT).show()
            return
        }
        val data = Intent().apply {
            putExtra(EXTRA_NAME, edit_text_name.text.toString())
            putExtra(EXTRA_DESKRIPSI, edit_text_description.text.toString())
            putExtra(EXTRA_PRIORITAS, number_picker_priority.value)
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}