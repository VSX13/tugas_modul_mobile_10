package com.example.aplikasilistdaftarpesanan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasilistdaftarpesanan.pesanan.Pesanan
import kotlinx.android.synthetic.main.pesanan_item.view.*

class PesananAdapter : ListAdapter<Pesanan, PesananAdapter.PesananHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pesanan>() {
            override fun areItemsTheSame(oldItem: Pesanan, newItem: Pesanan): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Pesanan, newItem: Pesanan): Boolean {
                return oldItem.name == newItem.name && oldItem.description == newItem.description
                        && oldItem.priority == newItem.priority
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesananHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.pesanan_item, parent, false)
        return PesananHolder(itemView)
    }

    override fun onBindViewHolder(holder: PesananHolder, position: Int) {
        val currentPesanan: Pesanan = getItem(position)

        holder.textViewName.text = currentPesanan.name
        holder.textViewPriority.text = currentPesanan.priority.toString()
        holder.textViewDescription.text = currentPesanan.description
    }

    fun getPesananAt(position: Int): Pesanan {
        return getItem(position)
    }

    inner class PesananHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewName: TextView = itemView.text_view_name
        var textViewPriority: TextView = itemView.text_view_priority
        var textViewDescription: TextView = itemView.text_view_description
    }

    interface OnItemClickListener {
        fun onItemClick(pesanan: Pesanan)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}