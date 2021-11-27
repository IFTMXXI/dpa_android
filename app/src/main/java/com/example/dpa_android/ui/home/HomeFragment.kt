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
import com.android.volley.toolbox.JsonObjectRequest
import com.example.dpa_android.AdapterProduto
import com.example.dpa_android.data.MySingleton

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
         /*   val textView =  view?.findViewById<TextView>(R.id.textView25)
            val url = "https://linuxjf.com/pdm2_aula2.php"
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response -> textView?.text ?:   "Resposta: $response" }
            ) { textView?.text = "Algo deu errado!" }

            MySingleton.getInstance(requireContext())!!.addToRequestQueue(JsonObjectRequest)
            /* MySingleton.getInstance(context = this)!!.addToRequestQueue(jsonObjectRequest) */
*/
            requisitar()
        }

        produtos.add(Produto("1 Produto",R.drawable.ic_dashboard_black_24dp,15.2f,"descrição"))
        produtos.add(Produto("2 Produto",R.drawable.ic_launcher_background,25.2f,"descrição"))
        produtos.add(Produto("3 Produto",R.drawable.ic_launcher_background,35.2f,"descrição"))
        produtos.add(Produto("4 Produto",R.drawable.ic_launcher_background,45.2f,"descrição"))
        produtos.add(Produto("5 Produto",R.drawable.ic_launcher_background,55.2f,"descrição"))
        produtos.add(Produto("6 Produto",R.drawable.ic_launcher_background,65.2f,"descrição"))
        produtos.add(Produto("7 Produto",R.drawable.ic_launcher_background,15.2f,"descrição"))
        produtos.add(Produto("8 Produto",R.drawable.ic_launcher_background,15.2f,"descrição"))
        produtos.add(Produto("9 Produto",R.drawable.ic_launcher_background,15.2f,"descrição"))

        val recyclerView_produtos = binding.RecyclerViewID
        recyclerView_produtos.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView_produtos.setHasFixedSize(true)

        val adapterProduto = AdapterProduto(this.requireContext(), produtos)
        recyclerView_produtos.adapter = adapterProduto

         fun onCreate(savedInstanceState: Bundle?) {}






        return root
    }


    fun requisitar() {
        val textView = view?.findViewById<TextView>(R.id.textView25)
        val url = "https://linuxjf.com/pdm2_aula2.php"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response -> textView?.text = "Resposta: $response" }
        ) { textView?.text = "Algo deu errado!" }
        MySingleton.getInstance(this.requireContext())!!.addToRequestQueue(jsonObjectRequest)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

