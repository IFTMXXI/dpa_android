package com.example.dpa_android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.dpa_android.R
import com.example.dpa_android.databinding.FragmentHomeBinding
import com.example.dpa_android.data.model.Produto
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.example.dpa_android.AdapterProduto
import com.example.dpa_android.data.MySingleton
import com.example.dpa_android.data.model.Produtos
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject


class HomeFragment : Fragment() {
    public fun getProducts(): ArrayList<Produto> {
        return produtos
    }
    var produtos: ArrayList<Produto> = ArrayList()
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var nome: String
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btnViewProduct: Button = binding.buttonViewProduct
        val btnViewNews: Button = binding.buttonViewNews
        val btnRequisitar: Button = binding.button4

        btnViewProduct.setOnClickListener { view ->
            view.findNavController().navigate(R.id.navigation_dashboard)
        }
        btnViewNews.setOnClickListener { view ->
            view.findNavController().navigate(R.id.navigation_notifications)
        }
        btnRequisitar.setOnClickListener {
            requisitarProdutos(produtos)
            val recyclerView_produtos = binding.RecyclerViewID
            recyclerView_produtos.layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView_produtos.setHasFixedSize(true)
            val adapterProduto = AdapterProduto(this.requireContext(), produtos)
            recyclerView_produtos.adapter = adapterProduto
        }


/*
        val recyclerView_produtos = binding.RecyclerViewID
        recyclerView_produtos.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView_produtos.setHasFixedSize(true)

        val adapterProduto = AdapterProduto(this.requireContext(), produtos)
        recyclerView_produtos.adapter = adapterProduto
*/
        fun onCreate(savedInstanceState: Bundle?) {

        }
        return root
    }


    private fun requisitarProdutos(  produtos: ArrayList<Produto> = ArrayList()
    ): ArrayList<Produto> {
        val k: ArrayList<Produto> = ArrayList()
        val textView = view?.findViewById<TextView>(R.id.textView25)
        val url = "https://denislima.com.br/xyz/controllers/Produtos/api.php"
        val jsonObjectRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val gson = Gson()
                val element = gson.fromJson(response, JsonElement::class.java)
                val e = element.asJsonArray
                var s = ""
                for (i in 0 until e.size()) {
                    val id = element.asJsonArray[i].asJsonObject["id"].asInt
                    val produtoss = element.asJsonArray[i].asJsonObject["produto"].asString
                    val descricao = element.asJsonArray[i].asJsonObject["descricao"].asString
                    val valor = element.asJsonArray[i].asJsonObject["valor"].asFloat
                    val qtdeEstoque = element.asJsonArray[i].asJsonObject["qtdeEstoque"].asInt
                    val categoria = element.asJsonArray[i].asJsonObject["categoria"].asString
                    val product: Produto =
                        Produto(produtoss, R.drawable.ic_launcher_background, valor, descricao)
                    produtos.add(product)
                }
            },
            { error ->
                textView?.text = "Error " + error
            },
        )
/*val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
               // textView?.text = "Resposta: " + response
                val jsonArray = response.getJSONArray("id")
                for (i in 0 until jsonArray.length()) {
                    val employee = jsonArray.getJSONObject(i)
                    val id = employee.getString("produto")
                    val age = employee.getInt("age")
                    val mail = employee.getString("mail")
                    //textView?.text = "R: " + id
                }
            },
            {
                    error -> textView?.text = "Error " + error
            }
        )*/
        MySingleton.getInstance(this.requireContext())!!.addToRequestQueue(jsonObjectRequest)
        return produtos
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




