package com.example.dpa_android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.dpa_android.R
import com.example.dpa_android.databinding.FragmentHomeBinding
import com.example.dpa_android.data.model.Produto
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.dpa_android.AdapterProduto
import com.example.dpa_android.data.MySingleton
import com.example.dpa_android.data.model.Noticia
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.Serializable

class HomeFragment() : Fragment() {
    public fun getProducts(): ArrayList<Produto> {
        return produtos
    }

    var produtos: ArrayList<Produto> = ArrayList()
    var p: ArrayList<Produto> = ArrayList()
    var noticias: ArrayList<Noticia> = ArrayList()
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btnViewProduct: Button = binding.buttonViewProduct
        val btnViewNews: Button = binding.buttonViewNews

        btnViewProduct.setOnClickListener { view ->
            view.findNavController().navigate(R.id.navigation_dashboard)
        }
        btnViewNews.setOnClickListener { view ->
            view.findNavController().navigate(R.id.navigation_notifications)
        }
        var url = "https://denislima.com.br/xyz/controllers/Produtos/api.php"
        var jsonObjectRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val gson = Gson()
                val element = gson.fromJson(response, JsonElement::class.java)
                val e = element.asJsonArray
                for (i in 0 until e.size()) {
                    val id = element.asJsonArray[i].asJsonObject["id"].asInt
                    val produtoss = element.asJsonArray[i].asJsonObject["produto"].asString
                    val descricao = element.asJsonArray[i].asJsonObject["descricao"].asString
                    val valor = element.asJsonArray[i].asJsonObject["valor"].asFloat
                    val qtdeEstoque = element.asJsonArray[i].asJsonObject["qtdeEstoque"].asInt
                    val categoria = element.asJsonArray[i].asJsonObject["categoria"].asString
                    val arquivo = element.asJsonArray[i].asJsonObject["arquivo"].asString
                    val product: Produto =
                        Produto(produtoss, arquivo, valor,qtdeEstoque, descricao,categoria)
                    produtos.add(product)
                }
                val recyclerView_produtos = binding.RecyclerViewID
                recyclerView_produtos.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                recyclerView_produtos.setHasFixedSize(true)
                val adapterProduto = AdapterProduto(this.requireContext(), produtos)
                recyclerView_produtos.adapter = adapterProduto
            },
            { error ->
                //textView?.text = "Error " + error
            },
        )
        MySingleton.getInstance(this.requireContext())!!.addToRequestQueue(jsonObjectRequest)
        url = "https://denislima.com.br/xyz/controllers/Noticias/api.php";
        jsonObjectRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val gson = Gson()
                val element = gson.fromJson(response, JsonElement::class.java)
                val e = element.asJsonArray
                var s = ""
                for (i in 0 until e.size()) {
                    if(!element.asJsonArray[i].isJsonNull) {
                        val id = element.asJsonArray[i].asJsonObject["id"].asInt
                        val produtoss = if(element.asJsonArray[i].asJsonObject["titulo"].isJsonNull) "" else element.asJsonArray[i].asJsonObject["titulo"].asString
                        val descricao = if(element.asJsonArray[i].asJsonObject["texto"].isJsonNull) "" else element.asJsonArray[i].asJsonObject["texto"].asString
                        val sintese = element.asJsonArray[i].asJsonObject["sintese"].asString
                        val categoria = element.asJsonArray[i].asJsonObject["tipoNoticia"].asString
                        val hora = element.asJsonArray[i].asJsonObject["hora"].asString
                        val arquivo = if(element.asJsonArray[i].asJsonObject["arquivo"].isJsonNull) "" else element.asJsonArray[i].asJsonObject["arquivo"].asString
                        val product: Noticia = Noticia(produtoss, arquivo, categoria,sintese, descricao, hora)
                        noticias.add(product)
                    }
                }
                loadNoticias()
            },
            { error ->
                //textView?.text = "Error " + error
            },
        )
        MySingleton.getInstance(this.requireContext())!!.addToRequestQueue(jsonObjectRequest)
        return root
    }

    fun loadNoticias() {
        var cards = ArrayList<NoticeView>();
        cards.add(NoticeView(binding.noticia, binding.nTitle, binding.nDesc, binding.imageNews1))
        cards.add(NoticeView(binding.noticia2, binding.nTitle2, binding.nDesc2, null))
        cards.add(NoticeView(binding.noticia3, binding.nTitle3, binding.nDesc3, null))
        for(i in 0 until cards.size) {
            cards[i].base.visibility = View.GONE;
        }
        val reversed = noticias.reversed();
        for(i in 0 until reversed.size) {
            if(i > 2) {
                break;
            }
            val card = cards[i];
            val data = reversed[i];
            card.base.visibility = View.VISIBLE;
            card.base.setOnClickListener { view ->
                val bundle = Bundle()
                bundle.putSerializable("noticia", reversed[i] as Serializable)
                view.findNavController().navigate(R.id.action_navigation_home_to_news, bundle)
            }
            if(card.image != null) {
                Picasso.get().load("https://denislima.com.br/xyz/uploads/Noticias/"+data.imagem).into(card.image);
            }
            card.title.text = data.nome;
            card.desc.text = data.hora;
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class NoticeView(baseView: View, title: TextView, desc: TextView, image: ImageView?) {
        val base : View = baseView
        val title = title
        val desc = desc
        val image = image
    }
}



