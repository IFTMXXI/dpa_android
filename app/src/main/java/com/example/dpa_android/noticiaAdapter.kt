package com.example.dpa_android

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.dpa_android.data.model.Noticia
import com.example.dpa_android.data.model.Produto
import com.squareup.picasso.Picasso

internal class noticiaAdapter (
        private val context: Context,
        private val noticia: ArrayList<Noticia>
    ) :
        BaseAdapter() {
        private var layoutInflater: LayoutInflater? = null
        private lateinit var imageView: ImageView
        private lateinit var nome: TextView
        private lateinit var valor: TextView
        private lateinit var quantidade: TextView
        override fun getCount(): Int {
            return noticia.size
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
                convertView = layoutInflater!!.inflate(R.layout.row_noticia, null)
            }

            imageView = convertView!!.findViewById(R.id.imageView)
            nome = convertView.findViewById(R.id.textView)
            valor = convertView.findViewById(R.id.textView2)
            quantidade = convertView.findViewById(R.id.textView6)
            Picasso.get().load("https://denislima.com.br/xyz/uploads/Noticias/"+noticia[position].imagem).into(imageView);
            nome.text = noticia[position].nome
            valor.text = "Categ" +noticia[position].categoria
            quantidade.text = "Sintese "+noticia[position].sintese
            return convertView
        }
    }