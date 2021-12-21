package com.example.dpa_android

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.dpa_android.data.MySingleton
import com.example.dpa_android.data.model.Cliente
import com.example.dpa_android.databinding.FragmentHomeBinding
import com.example.dpa_android.databinding.FragmentProdutosBinding
import com.example.dpa_android.placeholder.PlaceholderContent
import com.google.gson.Gson
import com.google.gson.JsonElement
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.dpa_android.databinding.ClienteItemListBinding
import com.google.gson.JsonNull
import java.io.Serializable


/**
 * A fragment representing a list of Items.
 */
class ClienteFragment : Fragment() {
    public fun getClientes(): ArrayList<Cliente> {
        return _clientes
    }

    private var columnCount = 1
    var _clientes: ArrayList<Cliente> = ArrayList()

    private lateinit var binding: ClienteItemListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.cliente_item_list, container, false)
        binding = ClienteItemListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val url = "https://denislima.com.br/xyz/controllers/Clientes/api.php"
        val jsonObjectRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val gson = Gson()
                val element = gson.fromJson(response, JsonElement::class.java)
                val e = element.asJsonArray
                for (i in 0 until e.size()) {
                    val data = element.asJsonArray[i];
                    var archive = "";
                    if(!data.asJsonObject["arquivo"].isJsonNull) {
                        archive = data.asJsonObject["arquivo"].asString;
                    }
                    val client: Cliente = Cliente(
                        data.asJsonObject["nome"].asString,
                        data.asJsonObject["cpf"].asString,
                        data.asJsonObject["email"].asString,
                        data.asJsonObject["dataNascimento"].asString,
                        data.asJsonObject["sexo"].asString,
                        archive
                    )
                    _clientes.add(client)
                }
                val recycler = binding.clientRecycler;
                recycler.layoutManager = LinearLayoutManager(context)
                recycler.adapter = ClienteRecyclerViewAdapter(this.requireContext(), _clientes)
                (recycler.adapter as ClienteRecyclerViewAdapter).onItemClick = { cliente ->
                    val bundle = Bundle()
                    bundle.putSerializable("cliente", cliente as Serializable)
                    root.findNavController().navigate(R.id.action_navigation_cliente_to_clientInfo, bundle)
                }
            },
            { error ->
                //textView?.text = "Error " + error
            },
        )
        MySingleton.getInstance(this.requireContext())!!.addToRequestQueue(jsonObjectRequest)
        return root
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
    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ClienteFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}