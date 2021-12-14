package com.example.dpa_android.ui.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest

import com.example.dpa_android.R
import com.example.dpa_android.data.MySingleton
import com.example.dpa_android.data.model.Noticia
import com.example.dpa_android.data.model.Produto

import com.example.dpa_android.databinding.FragmentNotificationsBinding
import com.example.dpa_android.noticiaAdapter
import com.google.gson.Gson
import com.google.gson.JsonElement
import java.io.Serializable

class NotificationsFragment : Fragment() {
    public fun getProducts(): ArrayList<Noticia> {
        return noticias
    }
    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    lateinit var gridView: GridView
    var thiscontext: Context? = null
    public var noticias: ArrayList<Noticia> = ArrayList()
    private var displayList: ArrayList<Noticia> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        thiscontext = getActivity()?.getApplicationContext()

        val btnSearch: Button = binding.noticiaSearch
        val searchInput: EditText = binding.searchInputNoticias

        btnSearch.setOnClickListener {
            displayList = noticias.filter { p -> p.nome.contains(searchInput.text.toString(),ignoreCase = true)} as ArrayList<Noticia>
            createGrid()
        }
        if(noticias.size != 0){
            noticias.clear()
        }
        val k: ArrayList<Noticia> = ArrayList()
        val url = "https://denislima.com.br/xyz/controllers/Noticias/api.php"
        val jsonObjectRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val gson = Gson()
                val element = gson.fromJson(response, JsonElement::class.java)
                val e = element.asJsonArray
                var s = ""
                for (i in 0 until e.size()) {
                    val id = element.asJsonArray[i].asJsonObject["id"].asInt
                    val produtoss = element.asJsonArray[i].asJsonObject["titulo"].asString
                    val descricao = element.asJsonArray[i].asJsonObject["texto"].asString
                    val sintese = element.asJsonArray[i].asJsonObject["sintese"].asString
                    val categoria = element.asJsonArray[i].asJsonObject["tipoNoticia"].asString
                    val arquivo = element.asJsonArray[i].asJsonObject["arquivo"].asString
                    val product: Noticia = Noticia(produtoss, arquivo, categoria,sintese, descricao)
                    noticias.add(product)

                }
                displayList = noticias
                createGrid()
            },
            { error ->
                //textView?.text = "Error " + error
            },
        )
        MySingleton.getInstance(this.requireContext())!!.addToRequestQueue(jsonObjectRequest)



        createGrid()
        return root
    }

    fun createGrid(){

        gridView = binding.noticiasGrid
        val mainAdapter = thiscontext?.let { noticiaAdapter(it, displayList) }
        gridView.adapter = mainAdapter

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val view = view
            if (view != null) {
                val bundle = Bundle()
                bundle.putSerializable("noticia", noticias[position] as Serializable)
                view.findNavController().navigate(R.id.navigationNews,bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}