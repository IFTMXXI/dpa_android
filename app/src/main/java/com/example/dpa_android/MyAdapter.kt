package com.example.dpa_android

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.dpa_android.data.model.Produto
import com.squareup.picasso.Picasso

internal class MainAdapter(
    private val context: Context,
    private val produto: ArrayList<Produto>
) :
    BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView
    private lateinit var nome: TextView
    private lateinit var valor: TextView
    override fun getCount(): Int {
        return produto.size
    }
    override fun getItem(position: Int): Any? {
        return null
    }
    override fun getItemId(position: Int): Long {
        return 0
    }
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View? {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.row_item, null)
        }

        imageView = convertView!!.findViewById(R.id.imageView)
        nome = convertView.findViewById(R.id.textView)
        valor = convertView.findViewById(R.id.textView2)

        Picasso.get().load("https://denislima.com.br/xyz/uploads/Produtos/0410210550261051809123.png").into(imageView);
        nome.text = produto[position].imagem
        //nome.text = produto[position].nome
        valor.text = "Valor: "+produto[position].valor
        return convertView
    }
}