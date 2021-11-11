package com.example.dpa_android.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.dpa_android.MainAdapter
import com.example.dpa_android.R
import com.example.dpa_android.data.model.Produto
import com.example.dpa_android.databinding.FragmentDashboardBinding
import com.example.dpa_android.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.io.Serializable


class DashboardFragment : Fragment() {
    public fun getProducts(): ArrayList<Produto> {
        return produtos
    }

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var gridView: GridView
    var thiscontext: Context? = null
    public var produtos: ArrayList<Produto> = ArrayList()
    private var displayList: ArrayList<Produto> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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

        produtos.add(Produto("1 Produto",R.drawable.ic_launcher_background,15.2f,"descrição"))
        produtos.add(Produto("2 Produto",R.drawable.ic_launcher_background,15.2f,"descrição"))
        produtos.add(Produto("3 Produto",R.drawable.ic_launcher_background,15.2f,"descrição"))
        produtos.add(Produto("4 Produto",R.drawable.ic_launcher_background,15.2f,"descrição"))
        produtos.add(Produto("5 Produto",R.drawable.ic_launcher_background,15.2f,"descrição"))
        produtos.add(Produto("6 Produto",R.drawable.ic_launcher_background,15.2f,"descrição"))
        produtos.add(Produto("7 Produto",R.drawable.ic_launcher_background,15.2f,"descrição"))
        produtos.add(Produto("8 Produto",R.drawable.ic_launcher_background,15.2f,"descrição"))
        produtos.add(Produto("9 Produto",R.drawable.ic_launcher_background,15.2f,"descrição"))

        displayList = produtos

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