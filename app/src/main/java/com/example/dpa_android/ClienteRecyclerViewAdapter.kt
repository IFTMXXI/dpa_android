package com.example.dpa_android

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.dpa_android.data.model.Cliente

import com.example.dpa_android.placeholder.PlaceholderContent.PlaceholderItem
import com.example.dpa_android.databinding.ClienteItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ClienteRecyclerViewAdapter(
    private val context: Context,
    private val values: MutableList<Cliente>
) : RecyclerView.Adapter<ClienteRecyclerViewAdapter.ViewHolder>() {
    var onItemClick: ((Cliente) -> Unit)? = null
    var clientes: MutableList<Cliente> = ArrayList();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        clientes = values;
        return ViewHolder(
            ClienteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.nome
        holder.contentView.text = item.email
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ClienteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(clientes[adapterPosition])
            }
        }
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}