package com.example.dpa_android

import android.content.Context
import android.graphics.BitmapFactory
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.dpa_android.data.model.Produto
import com.example.dpa_android.ui.home.HomeFragment
import android.graphics.Bitmap
import android.icu.number.NumberFormatter.with
import com.squareup.picasso.Picasso
import java.net.URL


class AdapterProduto(private val context: Context, private val produtos: MutableList<Produto>): RecyclerView.Adapter<AdapterProduto.ProdutoViewHolder>()  {
    var publicContext : Context? = null;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val itemLista = LayoutInflater.from(context).inflate(R.layout.produto_item, parent, false)
        val holder = ProdutoViewHolder(itemLista)
        publicContext = context;
        return holder
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
       holder.nome.text = produtos[position].nome
        holder.preco.text = produtos[position].valor.toString()

        Picasso.get().load("https://denislima.com.br/xyz/uploads/Produtos/"+produtos[position].imagem).into(holder.image);

    }

    override fun getItemCount(): Int {
        val tamanho = produtos.size
        if (tamanho < 6){
            return tamanho
        }else{
            return 6;
        }

    }

    inner class ProdutoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val nome = itemView.findViewById<TextView>(R.id.title)
        val image = itemView.findViewById<ImageView>(R.id.imageView)
        val preco = itemView.findViewById<TextView>(R.id.preco)

    }
}