package com.example.aplikasilistdaftarpesanan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasilistdaftarpesanan.pesanan.Pesanan
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val ADD_PESANAN_REQUEST = 1
        const val EDIT_PESANAN_REQUEST = 2
    }
    private lateinit var pesananViewModel: PesananViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonAddPesanan.setOnClickListener {
            startActivityForResult(
                Intent(this, AddEditPesananActivity::class.java),
                ADD_PESANAN_REQUEST
            )
        }
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        val adapter = PesananAdapter()
        recycler_view.adapter = adapter
        pesananViewModel = ViewModelProviders.of(this).get(PesananViewModel::class.java)
        pesananViewModel.getAllPesanan().observe(this, Observer<List<Pesanan>> {
            adapter.submitList(it)
        })
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                pesananViewModel.delete(adapter.getPesananAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Pesanan dihapus!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)
        adapter.setOnItemClickListener(object : PesananAdapter.OnItemClickListener {
            override fun onItemClick(pesanan: Pesanan) {
                val intent = Intent(baseContext, AddEditPesananActivity::class.java)
                intent.putExtra(AddEditPesananActivity.EXTRA_ID, pesanan.id)
                intent.putExtra(AddEditPesananActivity.EXTRA_NAME, pesanan.name)
                intent.putExtra(AddEditPesananActivity.EXTRA_DESKRIPSI, pesanan.description)
                intent.putExtra(AddEditPesananActivity.EXTRA_PRIORITAS, pesanan.priority)
                startActivityForResult(intent, EDIT_PESANAN_REQUEST)
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_pesanan -> {
                pesananViewModel.deleteAllPesanan()
                Toast.makeText(this, "Semua telah terhapus!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_PESANAN_REQUEST && resultCode == Activity.RESULT_OK) {
            val newPesanan = Pesanan(
                data!!.getStringExtra(AddEditPesananActivity.EXTRA_NAME).toString(),
                data.getStringExtra(AddEditPesananActivity.EXTRA_DESKRIPSI).toString(),
                data.getIntExtra(AddEditPesananActivity.EXTRA_PRIORITAS, 1)
            )
            pesananViewModel.insert(newPesanan)
            Toast.makeText(this, "Pesanan disimpan!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_PESANAN_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditPesananActivity.EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(this, "Pembaharuan gagal!", Toast.LENGTH_SHORT).show()
            }
            val updatePesanan = Pesanan(
                data!!.getStringExtra(AddEditPesananActivity.EXTRA_NAME).toString(),
                data.getStringExtra(AddEditPesananActivity.EXTRA_DESKRIPSI).toString(),
                data.getIntExtra(AddEditPesananActivity.EXTRA_PRIORITAS, 1)
            )
            updatePesanan.id = data.getIntExtra(AddEditPesananActivity.EXTRA_ID, -1)
            pesananViewModel.update(updatePesanan)
        } else {
            Toast.makeText(this, "Pesanan tidak tersimpan!", Toast.LENGTH_SHORT).show()
        }
    }
}