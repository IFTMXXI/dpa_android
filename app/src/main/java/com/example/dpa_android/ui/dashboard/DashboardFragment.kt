package com.example.dpa_android.ui.dashboard

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.dpa_android.AdapterProduto
import com.example.dpa_android.MainAdapter
import com.example.dpa_android.R
import com.example.dpa_android.data.MySingleton
import com.example.dpa_android.data.model.Produto
import com.example.dpa_android.databinding.FragmentDashboardBinding
import com.example.dpa_android.databinding.FragmentHomeBinding
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_news.*
import java.io.Serializable


class DashboardFragment : Fragment() {
    public fun getProducts(): ArrayList<Produto> {
        return produtos
    }

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    lateinit var gridView: GridView
    var thiscontext: Context? = null
    public var produtos: ArrayList<Produto> = ArrayList()
    private var displayList: ArrayList<Produto> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,

    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding =  FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        thiscontext = getActivity()?.getApplicationContext()

        val btnSearch: Button = binding.search
        val searchInput: EditText = binding.searchInput
        val btn: Button = binding.button
        btn.setOnClickListener { view ->
            view.findNavController().navigate(R.id.navigationCreateProduct)
        }
        btnSearch.setOnClickListener {
            displayList = produtos.filter { p -> p.nome.contains(searchInput.text.toString(),ignoreCase = true)} as ArrayList<Produto>
            createGrid()
        }

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
                displayList = produtos
                createGrid()
            },
            { error ->
                textView?.text = "Error " + error
            },
        )
        MySingleton.getInstance(this.requireContext())!!.addToRequestQueue(jsonObjectRequest)



        createGrid()
        return root
    }


    fun createGrid(){
        gridView = binding.productGrid
        val mainAdapter = thiscontext?.let { MainAdapter(it, displayList) }
        gridView.adapter = mainAdapter
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val view = view
            if (view != null) {
                val bundle = Bundle()
                bundle.putSerializable("produto", produtos[position] as Serializable)
                view.findNavController().navigate(R.id.navigationProdutos,bundle)
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}