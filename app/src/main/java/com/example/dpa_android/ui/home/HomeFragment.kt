package com.example.dpa_android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.dpa_android.R
import com.example.dpa_android.databinding.FragmentHomeBinding
import com.example.dpa_android.data.model.Produto
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.dpa_android.AdapterProduto
import com.example.dpa_android.data.MySingleton
import com.google.gson.Gson
import com.google.gson.JsonElement
import java.io.IOException

import android.graphics.BitmapFactory

import android.graphics.Bitmap

import java.io.InputStream

import java.net.HttpURLConnection

import java.net.URL
import java.io.ByteArrayOutputStream

import java.io.BufferedInputStream








class HomeFragment() : Fragment() {
    public fun getProducts(): ArrayList<Produto> {
        return produtos
    }

    var produtos: ArrayList<Produto> = ArrayList()
    var p: ArrayList<Produto> = ArrayList()
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val url = "https://denislima.com.br/xyz/controllers/Produtos/api.php"
        val jsonObjectRequest = StringRequest(
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
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




